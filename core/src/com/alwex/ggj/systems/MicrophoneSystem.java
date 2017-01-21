package com.alwex.ggj.systems;

import com.alwex.ggj.components.MicrophoneComponent;
import com.alwex.ggj.components.PositionComponent;
import com.alwex.ggj.events.NoFishEvent;
import com.alwex.ggj.events.ThrowFishEvent;
import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polyline;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;
import org.jtransforms.dst.FloatDST_1D;
import org.jtransforms.fft.FloatFFT_1D;
/**
 * Created by Isaac on 20/01/2017.
 */
@Wire
public class MicrophoneSystem extends EntityProcessingSystem {

    int count;

    AudioRecorder recorder;
    short[] shortPCM;
    float[] floats;
    float[][] buffer;
    float[] oldSz;
    float[] maxHeight;
    int tick;
    Polyline[] lines;
    float volume, effect, effect2;
    FloatDST_1D fourierTransform;
    ShapeRenderer shapeRenderer;
    EventSystem eventSystem;
    boolean canThrowFish;


    ComponentMapper<PositionComponent> positionMapper;
    WaterSystem waterSystem;

    public MicrophoneSystem(AudioRecorder recorder,  ShapeRenderer shapeRenderer,int count) {
        super(Aspect.all(PositionComponent.class));
        this.recorder = recorder;
        this.count = count;
        this.shapeRenderer = shapeRenderer;
    }

    public static float[] floatMe(short[] pcms) {
        float[] floaters = new float[pcms.length];
        for (int i = 0; i < pcms.length; i++) {
            floaters[i] = pcms[i];
        }
        return floaters;
    }

    @Subscribe
    protected void noFishEventListener(NoFishEvent event){
        canThrowFish = true;
    }

    @Override
    protected void begin() {

        if (effect < 32 && canThrowFish) {
            if (!(tick-- > 0)) {
                recorder.read(this.shortPCM, 0, this.shortPCM.length);
                tick = 10;
            }
            this.floats = floatMe(shortPCM);
            this.fourierTransform.forward(floats, true);

            volume = 0;
            for (int i = 0; i < this.shortPCM.length; i++) {
                volume = Math.max(this.shortPCM[i], volume);
            }

            for (int i = 0; i < count; i++) {
                this.maxHeight[i] = 0;

            }
            for (int hz = 1; hz < 32; hz++) {


                float sz = this.floats[hz];


                float weight = 20.0f;
                oldSz[hz] = ((oldSz[hz] * weight) + (sz / (volume * volume / 100000f + 200f))) / (weight + 1.0f);

                lines[hz].setScale(1f, oldSz[hz]);

                float[] transformed = lines[hz].getTransformedVertices();

                for (int i = 0; i < count; i++) {
                    this.maxHeight[i] += transformed[i * 2 + 1];//Math.max(this.maxHeight[i],);
                }
            }

            effect2 = 0;
            for (int i = 0; i < this.maxHeight.length; i++) {
                if(this.maxHeight[i] > 7) {
                    effect2 += (this.maxHeight[i]);
                }
            }

            effect2 = Math.abs(effect2);
        } else {

        }

    }

    @Override
    protected void process(Entity e) {}

    @Override
    protected void end() {
        if (effect < 32) {
            for (Entity springEntity : waterSystem.getSpringList()) {
                PositionComponent springPos = positionMapper.get(springEntity);
                int i = (int) (springPos.x * 4);
                springPos.y += (maxHeight[i]) * (effect2 / 100000f + (effect * effect) / 1296) * 0.05f;

            }
            effect += effect2 / 100000f;


        } else {
            float[] velocityArray = new float[waterSystem.getSpringList().size()];

            for (Entity springEntity : waterSystem.getSpringList()) {
                PositionComponent springPos = positionMapper.get(springEntity);
                int i = (int) (springPos.x);
                velocityArray[i] = springPos.y;
            }
            eventSystem.dispatch(new ThrowFishEvent(velocityArray));
            effect = 0;
            effect2 = 0;
            for (int i = 0; i < this.maxHeight.length; i++) {
                this.maxHeight[i] = 0;
            }
            canThrowFish = false;
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.box(0, 3, 0, effect, 1, 0);
        shapeRenderer.end();
    }

    @Override
    protected void initialize() {
        this.fourierTransform = new FloatDST_1D(count/8);
        this.shortPCM = new short[count];
        this.floats = new float[count];
        this.oldSz = new float[count];
        this.maxHeight = new float[count];
        this.canThrowFish = true;

        buffer = new float[128][count*2];
        lines = new Polyline[128];
        for (int hz = 0; hz<32; hz++) {
            for (int i = 0; i < count; i++) {
                buffer[hz][i * 2] = i;
                buffer[hz][i * 2 + 1] = MathUtils.sin(i / 40f * hz);
            }
            lines[hz] = new Polyline(buffer[hz]);
        }


    }
}
