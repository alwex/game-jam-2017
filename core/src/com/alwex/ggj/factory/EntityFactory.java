package com.alwex.ggj.factory;

import com.alwex.ggj.components.*;
import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by samsung on 21/01/2017.
 */
public class EntityFactory {
    public static EntityFactory instance = new EntityFactory();

    public Entity createFish(
            World world,
            String name,
            float x, float y, float vx, float vy,
            float width, float height
    ) {
        return world.createEntity()
                .edit()
                .add(new FishComponent())
                .add(new PositionComponent(x, y))
                .add(new ShapeComponent(width, height))
                .add(new SliceableComponent())
                .add(new SpriteComponent(name))
                .add(new PhysicComponent())
                .getEntity();
    }
}
