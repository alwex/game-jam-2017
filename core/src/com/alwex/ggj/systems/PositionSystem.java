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

    public PositionSystem() {
        super(Aspect.all(PositionComponent.class));
    }

    @Override
    protected void begin() {
    }

    @Override
    protected void process(Entity e) {

        PositionComponent p = positionMapper.get(e);
        //p.x += 0.1f;
    }

    @Override
    protected void end() {

    }
}
