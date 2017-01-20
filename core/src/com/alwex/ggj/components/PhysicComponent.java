package com.alwex.ggj.components;

import com.artemis.Component;

/**
 * Created by alexandreguidet on 20/01/17.
 */
public class PhysicComponent extends Component {
    public float mass, vx, vy;
    public PhysicComponent(float mass, float vx, float vy) {
        this.mass = mass;
        this.vx = vx;
        this.vy = vy;
    }

    public PhysicComponent() {

    }
}
