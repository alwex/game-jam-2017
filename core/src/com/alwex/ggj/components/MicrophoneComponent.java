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
    public float sz;
    public int count;

    public MicrophoneComponent() {

    }
    public MicrophoneComponent(int hz, int count) {
        this.hz = hz;
        this.count = count;
    }
}
