package com.alwex.ggj.components;

import com.artemis.Component;

/**
 * Created by alexandreguidet on 20/01/17.
 */
public class PositionComponent extends Component {
    public float x, y;

    public PositionComponent() {

    }

    public PositionComponent(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
