package com.alwex.ggj.components;

import com.artemis.Component;

/**
 * Created by samsung on 21/01/2017.
 */
public class RotationComponent extends Component {
    public float intensity = 0, angle = 0;

    public RotationComponent() {
    }

    public RotationComponent(float angle, float intensity) {
        this.intensity = intensity;
        this.angle = angle;
    }
}
