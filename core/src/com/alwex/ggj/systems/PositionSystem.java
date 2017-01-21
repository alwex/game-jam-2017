package com.alwex.ggj.systems;

import com.alwex.ggj.components.PhysicComponent;
import com.alwex.ggj.components.PositionComponent;
import com.alwex.ggj.components.SpriteComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;

import javax.swing.text.Position;

/**
 * Created by samsung on 20/01/2017.
 */
@Wire
public class PositionSystem extends EntityProcessingSystem {

    ComponentMapper<PositionComponent> positionMapper;
    ComponentMapper<PhysicComponent> physicMapper;

    public PositionSystem() {
        super(Aspect.all(PositionComponent.class, PhysicComponent.class));
    }

    DeltaSystem deltaSystem;

    @Override
    protected void begin() {
    }

    @Override
    protected void process(Entity e) {

        PositionComponent p = positionMapper.get(e);
        PhysicComponent psx = physicMapper.get(e);

        p.x += psx.vx * deltaSystem.getDelta();
        p.y += psx.vy * deltaSystem.getDelta();

    }

    @Override
    protected void end() {

    }
}
