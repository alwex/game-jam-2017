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
    ComponentMapper<PositionComponent> positionMapper;

    float mapWidth, mapHeight;

    float gravity;
    public PhysicSystem(float gravity, float mapWidth, float mapHeight) {
        super(Aspect.all(PhysicComponent.class).all(PositionComponent.class));
        this.gravity = gravity;

        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    @Override
    protected void process(Entity e) {
        PhysicComponent psx = physicMapper.get(e);
        PositionComponent p = positionMapper.get(e);

        p.x += psx.vx * 0.1f / psx.mass;
        p.y += psx.vy * 0.1f / psx.mass;

        //psx.vx *= 0.99f;
        //psx.vy *= 0.99f;


        psx.vy += gravity * -0.1f;

    }

    @Override
    protected void initialize() {
        float resolution = 0.25f;

        for(int i=0; i<200; i++) {

            Entity e = world.createEntity()
                    .edit()
                    .add(new PositionComponent( mapWidth / 2, mapHeight/2))
                    .add(new PhysicComponent(MathUtils.random(1f, 2f), MathUtils.random(-1f, 1f), MathUtils.random(0f, 2f)))
                    .add(new ShapeComponent(1*resolution, 1*resolution))
                    .getEntity();
        }

    }
}
