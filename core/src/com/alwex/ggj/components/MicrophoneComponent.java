package com.alwex.ggj.components;

import com.artemis.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polyline;

/**
 * Created by Isaac on 21/01/2017.
 */
public class MicrophoneComponent extends Component {
    public int hz;
    float sz;
    float[] buffer;
    int count;

    Polyline line;

    public MicrophoneComponent() {

    }
    public MicrophoneComponent(int hz, int count) {
        this.hz = hz;
        this.count = count;
        buffer = new float[count*2];
        for (int i = 0; i < count; i++) {
            buffer[i * 2] = i;
            buffer[i * 2 + 1] = MathUtils.sin(i/40f*hz);//-0.5f;
        }
        this.line = new Polyline(buffer);
    }

    public void setSize(float sz){

        float weight = 10.0f;
        this.sz = (this.sz*weight + sz )/(weight + 1.0f);


        line.setScale(5.0f, this.sz / 10.0f);
    }

    public Polyline getLine() {
        return line;
    }
}
