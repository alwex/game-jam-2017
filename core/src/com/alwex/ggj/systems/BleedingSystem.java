package com.alwex.ggj.systems;

import com.alwex.ggj.components.BleedingComponent;
import com.alwex.ggj.components.PositionComponent;
import com.alwex.ggj.factory.EntityFactory;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.artemis.systems.IntervalEntitySystem;
import com.badlogic.gdx.math.MathUtils;

import javax.swing.text.Position;

/**
 * Created by samsung on 21/01/2017.
 */
public class BleedingSystem extends IntervalEntityProcessingSystem {

    ComponentMapper<PositionComponent> positionMapper;

    public BleedingSystem() {
        super(Aspect.all(PositionComponent.class, BleedingComponent.class), 0.15f);
    }

    @Override
    protected void process(Entity e) {
        PositionComponent position = positionMapper.get(e);

        EntityFactory.instance.createBloodDrop(
                world,
                position.x,
                position.y,
                MathUtils.random(-5f, 5f),
                MathUtils.random(-1f, 10f)
        );
    }
}
