package com.alwex.ggj.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by samsung on 21/01/2017.
 */
public class BloodDropComponent extends Component {
    public float width, height;
    public Color color;

    public BloodDropComponent() {
    }

    public BloodDropComponent(float width, float height, Color color) {
        this.width = width;
        this.height = height;
        this.color = color;
    }
}
