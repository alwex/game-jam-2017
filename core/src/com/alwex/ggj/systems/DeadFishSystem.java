package com.alwex.ggj.systems;

import com.alwex.ggj.components.DeadComponent;
import com.alwex.ggj.components.PositionComponent;
import com.alwex.ggj.factory.EntityFactory;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

/**
 * Created by samsung on 21/01/2017.
 */
public class DeadFishSystem extends EntityProcessingSystem {

    ComponentMapper<PositionComponent> positionMapper;

    public DeadFishSystem() {
        super(Aspect.all(DeadComponent.class));
    }

    @Override
    protected void process(Entity e) {
//        PositionComponent position = positionMapper.get(e);
//        if (position.y < 0) {
//            world.deleteEntity(e);
//            e.deleteFromWorld();
//        }
    }
}
