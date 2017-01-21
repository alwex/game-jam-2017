package com.alwex.ggj.systems;

import com.alwex.ggj.components.*;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.MathUtils;


/**
 * Created by jbrungar on 20/01/17.
 */
@Wire
public class FishSystem extends EntityProcessingSystem {

    float mapWidth, mapHeight;
    ComponentMapper<PositionComponent> positionMapper;
    ComponentMapper<DeadComponent> deadMapper;

    public FishSystem(float mapWidth, float mapHeight) {
        super(Aspect.all(FishComponent.class));

        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void process(Entity e) {
        PositionComponent pos = positionMapper.get(e);
        if (deadMapper.has(e)) {
            pos.y -= 0.2f;
            if (pos.y < 0) {
                world.deleteEntity(e);
            }
        } else {
            pos.y += 0.1f;
        }
    }

    public void spawn() {
        world.createEntity()
                .edit()
                .add(new FishComponent())
                .add(new PositionComponent(MathUtils.random(0, mapWidth), 0))
                .add(new ShapeComponent(1f, 2f))
                .add(new SliceableComponent())
                .add(new SpriteComponent("one"))
                .getEntity();
    }
}
