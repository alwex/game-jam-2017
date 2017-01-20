package com.alwex.ggj.systems;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioRecorder;

/**
 * Created by Isaac on 20/01/2017.
 */
public class MicrophoneSystem extends BaseSystem {

    AudioRecorder recorder;

    public MicrophoneSystem(AudioRecorder recorder) {
        this.recorder = recorder;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void processSystem() {

        short[] shortPCM = new short[1024]; // 1024 samples
        recorder.read(shortPCM, 0, shortPCM.length);

        //Gdx.app.log(this.getClass().getSimpleName(), Integer.toString(shortPCM[512]));

        String message = "";

        for(int i=0; i<180; i++) {
            if(shortPCM[i] > 200 ) {
                message += "W";
            } else {
                message += " ";
            }
        }
        Gdx.app.log("-", message);


    }
}
