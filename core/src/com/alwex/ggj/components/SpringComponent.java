package com.alwex.ggj.components;

import com.artemis.Component;
import com.artemis.annotations.EntityId;

/**
 * Created by samsung on 20/01/2017.
 */
public class SpringComponent extends Component {
    public float v, a, startY;
    @EntityId
    public int leftEntity = -1;
    @EntityId
    public int rightEntity = -1;

    public SpringComponent() {

    }

    public SpringComponent(float v, float a, float startY, int leftEntity, int rightEntity) {
        this.v = v;
        this.a = a;
        this.startY = startY;
    }
}
