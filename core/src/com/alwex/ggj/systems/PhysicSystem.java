package com.alwex.ggj.systems;

import com.alwex.ggj.components.PhysicComponent;
import com.alwex.ggj.components.PositionComponent;
import com.alwex.ggj.components.ShapeComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Isaac on 21/01/2017.
 */
public class PhysicSystem extends EntityProcessingSystem {

    ComponentMapper<PhysicComponent> physicMapper;

    float mapWidth, mapHeight;

    float gravity;
    public PhysicSystem(float gravity, float mapWidth, float mapHeight) {
        super(Aspect.all(PhysicComponent.class));
        this.gravity = gravity;

        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    @Override
    protected void process(Entity e) {
        PhysicComponent psx = physicMapper.get(e);

        psx.vy += world.getDelta() * psx.mass * gravity;

    }

    @Override
    protected void initialize() {


    }
}
