package com.alwex.ggj.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by samsung on 21/01/2017.
 */
public class BloodStainComponent extends Component {
    public float x, y, radius;
    public Color color;

    public BloodStainComponent() {
    }

    public BloodStainComponent(float x, float y, float radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }
}
