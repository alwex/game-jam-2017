package com.alwex.ggj.systems;

import com.alwex.ggj.components.PositionComponent;
import com.alwex.ggj.components.SpringComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;

/**
 * Created by samsung on 21/01/2017.
 */
@Wire
public class GarbageSystem extends EntityProcessingSystem {

    ComponentMapper<PositionComponent> positionMapper;

    float torelance = 3f;

    public GarbageSystem() {
        super(Aspect.all(PositionComponent.class).exclude(SpringComponent.class));
    }

    @Override
    protected void process(Entity e) {
        PositionComponent position = positionMapper.get(e);
        if (position.y + torelance < 0) {
            e.deleteFromWorld();
        }
    }
}
