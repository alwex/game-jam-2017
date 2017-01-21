package com.alwex.ggj.systems;

import com.alwex.ggj.components.RotationComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;

/**
 * Created by samsung on 21/01/2017.
 */
@Wire
public class RotationSystem extends EntityProcessingSystem {

    DeltaSystem deltaSystem;
    ComponentMapper<RotationComponent> rotationMapper;

    public RotationSystem() {
        super(Aspect.all(RotationComponent.class));
    }

    @Override
    protected void process(Entity e) {
        RotationComponent rotation = rotationMapper.get(e);
        rotation.angle += rotation.intensity * deltaSystem.getDeltaFactor();
    }
}
