package com.alwex.ggj.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by samsung on 20/01/2017.
 */
public class ShapeComponent extends Component {
    public float width, height, scale = 1;

    public ShapeComponent() {

    }

    public ShapeComponent(float width, float height) {
        this.width = width;
        this.height = height;
    }
}
