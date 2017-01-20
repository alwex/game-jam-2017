package com.alwex.ggj.systems;

import com.alwex.ggj.components.PositionComponent;
import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.math.MathUtils;
import org.jtransforms.dst.FloatDST_1D;
/**
 * Created by Isaac on 20/01/2017.
 */
public class MicrophoneSystem extends EntityProcessingSystem {

    AudioRecorder recorder;
    short[] shortPCM;
    float[] floats;
    FloatDST_1D fourierTransform;


    ComponentMapper<PositionComponent> positionMapper;

    public MicrophoneSystem(AudioRecorder recorder) {
        super(Aspect.all(PositionComponent.class));
        this.recorder = recorder;
    }

    public static float[] floatMe(short[] pcms) {
        float[] floaters = new float[pcms.length];
        for (int i = 0; i < pcms.length; i++) {
            floaters[i] = pcms[i];
        }
        return floaters;
    }


    @Override
    protected void begin() {

        recorder.read(this.shortPCM, 0, this.shortPCM.length);
        this.floats = floatMe(shortPCM);
        this.fourierTransform.forward(floats,false);

    }

    float[] buffer;
    @Override
    protected void process(Entity e) {

        float pi = (float)Math.PI;

        PositionComponent p = positionMapper.get(e);

        float total = 0;
        for (int i=0; i<10; i++) {
            float f = this.floats[i];
            total += Math.abs(f) * MathUtils.sin((p.x * pi)  / ((float)i) / 10.0f )*0.1f;
        }


        float weight = 40.0f;

        this.buffer[(int)(p.x/32f)] = (this.buffer[(int)(p.x/32f)]*weight + total) / (weight+1);// (total + this.buffer[(int)(p.x/32)])/2.0f;

        //p.y = this.floats[(int)(p.x/8)] / 400.0f + 200;//total/100 + 200;
        p.y = this.buffer[(int)(p.x/32f)]/100 + 200;
    }

    @Override
    protected void initialize() {
        this.fourierTransform = new FloatDST_1D(128);
        this.shortPCM = new short[128];
        this.floats = new float[128];
        this.buffer = new float[128];
    }
}
