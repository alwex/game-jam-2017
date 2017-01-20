package com.alwex.ggj.systems;

import com.alwex.ggj.components.MicrophoneComponent;
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
import org.jtransforms.fft.FloatFFT_1D;
/**
 * Created by Isaac on 20/01/2017.
 */
public class MicrophoneSystem extends EntityProcessingSystem {

    AudioRecorder recorder;
    short[] shortPCM;
    float[] floats;
    int count;
    FloatDST_1D fourierTransform;


    ComponentMapper<MicrophoneComponent> microphoneMapper;

    public MicrophoneSystem(AudioRecorder recorder, int count) {
        super(Aspect.all(MicrophoneComponent.class));
        this.recorder = recorder;
        this.count = count;
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
        this.fourierTransform.forward(floats,true);

    }

    @Override
    protected void process(Entity e) {


        MicrophoneComponent mic = microphoneMapper.get(e);

        mic.setSize(this.floats[mic.hz]);


    }

    @Override
    protected void initialize() {
        this.fourierTransform = new FloatDST_1D(count/8);
        this.shortPCM = new short[count];
        this.floats = new float[count];
    }
}
