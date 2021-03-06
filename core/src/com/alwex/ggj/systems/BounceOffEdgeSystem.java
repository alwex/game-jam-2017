package com.alwex.ggj.systems;

import com.alwex.ggj.components.*;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by jbrungar on 21/01/17.
 */
@Wire
public class BounceOffEdgeSystem extends EntityProcessingSystem {

    ComponentMapper<PositionComponent> positionMapper;
    ComponentMapper<ShapeComponent> shapeMapper;
    ComponentMapper<PhysicComponent> physicMapper;

    float mapWidth, mapHeight;

    public BounceOffEdgeSystem(float mapWidth, float mapHeight) {
        super(Aspect.all(SliceableComponent.class, PhysicComponent.class).exclude(DeadComponent.class));
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    @Override
    protected void process(Entity e) {
        PositionComponent position = positionMapper.get(e);
        ShapeComponent shape = shapeMapper.get(e);
        PhysicComponent physic = physicMapper.get(e);
        if (position.x <= 0 || position.x + shape.width >= mapWidth) {
            physic.vx *= -1;
            position.x = MathUtils.clamp(position.x,0.01f,position.x + shape.width - 0.01f);


        }
        if (physic.vy > 0 && position.y + shape.height >= mapHeight) {
            physic.vy = 0;
            position.y = position.y + shape.height - 0.01f;
        }
    }
}
