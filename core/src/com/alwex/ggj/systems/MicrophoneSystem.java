package com.alwex.ggj.systems;

import com.alwex.ggj.components.MicrophoneComponent;
import com.alwex.ggj.components.PositionComponent;
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
import org.jtransforms.dst.FloatDST_1D;
import org.jtransforms.fft.FloatFFT_1D;
/**
 * Created by Isaac on 20/01/2017.
 */
@Wire
public class MicrophoneSystem extends EntityProcessingSystem {

    AudioRecorder recorder;
    short[] shortPCM;
    float[] floats;
    float[][] buffer;
    float[] oldSz;
    float[] maxHeight;
    Polyline[] lines;
    int count;
    FloatDST_1D fourierTransform;
    float oldVolume, volume, volume2;
    ShapeRenderer shapeRenderer;


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

    int tick = 0;
    @Override
    protected void begin() {

        if(!(tick-->0)) {
            recorder.read(this.shortPCM, 0, this.shortPCM.length);
            tick = 10;
        }
        this.floats = floatMe(shortPCM);
        this.fourierTransform.forward(floats,true);

        volume = 0;
        for (int i=0; i<this.shortPCM.length; i++){
            volume = Math.max(this.shortPCM[i],volume);
        }


        for (int i = 0; i < count; i++) {
            this.maxHeight[i] = 0;
        }
        for (int hz = 1; hz < 32; hz++) {


            float sz = this.floats[hz];


            float weight = 10.0f;
            oldSz[hz] = ((oldSz[hz] * weight * (volume+1000)) + sz) / (weight + 1.0f)/(volume+1000);


            lines[hz].setScale(1f, oldSz[hz]);

            float[] transformed = lines[hz].getTransformedVertices();

            for (int i = 0; i < count; i++) {
                this.maxHeight[i] += transformed[i * 2 + 1];//Math.max(this.maxHeight[i],);
            }
        }





    }

    @Override
    protected void process(Entity e) {}

    @Override
    protected void end() {
        if(true) {
            for (Entity springEntity : waterSystem.getSpringList()) {
                PositionComponent springPos = positionMapper.get(springEntity);
                int i = (int) (springPos.x * 4);
                springPos.y += (maxHeight[i])/10f;

                volume2 += Math.abs(maxHeight[i])*0.2f;
            }
        }
        volume2 -=  8.0f;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.box(16,1,0,(volume2)/10f,1,0);
        if(tick==5){Gdx.app.log("Volume",""+(volume2)/10f);}
        shapeRenderer.end();
    }

    @Override
    protected void initialize() {
        this.fourierTransform = new FloatDST_1D(count/8);
        this.shortPCM = new short[count];
        this.floats = new float[count];
        this.oldSz = new float[count];
        this.maxHeight = new float[count];


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
