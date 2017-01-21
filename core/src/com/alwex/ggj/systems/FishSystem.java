package com.alwex.ggj.systems;

import com.alwex.ggj.components.*;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import net.mostlyoriginal.api.utils.BagUtils;


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
                .getEntity();
    }

    public Vector2 centreOfMass() {
        Bag<Entity> fishEntities = this.getEntities();
        Entity[] fishArray = fishEntities.getData();
        float sumX = 0;
        float sumY = 0;
        for (int i = 0; i < fishEntities.size(); i++) {
            PositionComponent pos = positionMapper.get(fishArray[i]);
            sumX += pos.x;
            sumY += pos.y;
        }
        return new Vector2(sumX / fishEntities.size(), sumY / fishEntities.size());
    }
}
