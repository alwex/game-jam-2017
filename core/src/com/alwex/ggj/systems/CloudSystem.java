package com.alwex.ggj.systems;

import com.alwex.ggj.components.CloudComponent;
import com.alwex.ggj.components.PositionComponent;
import com.alwex.ggj.components.ShapeComponent;
import com.alwex.ggj.factory.CloudDescriptor;
import com.alwex.ggj.factory.EntityFactory;
import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

/**
 * Created by samsung on 21/01/2017.
 */
@Wire
public class CloudSystem extends EntityProcessingSystem {

    float mapWidth, mapHeight;

    ComponentMapper<PositionComponent> positionMapper;
    ComponentMapper<ShapeComponent> shapeMapper;

    ArrayList<CloudDescriptor> cloudsDesciptors;

    public CloudSystem(float mapWidth, float mapHeight) {
        super(Aspect.all(CloudComponent.class, PositionComponent.class, ShapeComponent.class));

        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    @Override
    protected void initialize() {
        cloudsDesciptors = new ArrayList<CloudDescriptor>();
        cloudsDesciptors.add(new CloudDescriptor("cloud1", 2, 1));
        cloudsDesciptors.add(new CloudDescriptor("cloud2", 2, 1));
        cloudsDesciptors.add(new CloudDescriptor("cloud3", 4, 2));
        cloudsDesciptors.add(new CloudDescriptor("cloud4", 4, 1));
        cloudsDesciptors.add(new CloudDescriptor("cloud5", 4, 2));
        cloudsDesciptors.add(new CloudDescriptor("cloud6", 8, 3));
        cloudsDesciptors.add(new CloudDescriptor("cloud7", 12, 4));

        for (int i = 16; i <= mapHeight; i++) {
            int cloudId = MathUtils.random(cloudsDesciptors.size() - 1);
            EntityFactory.instance.createCloud(
                    world,
                    cloudsDesciptors.get(cloudId),
                    MathUtils.random(0, mapWidth), i,
                    MathUtils.random(0f, 0.5f),
                    0
            );

        }
    }

    @Override
    protected void process(Entity e) {
        PositionComponent position = positionMapper.get(e);
        ShapeComponent shape = shapeMapper.get(e);

        if (position.x < 0 - (shape.width * 2)) {
            position.x = mapWidth + shape.width;
        }

        if (position.x > mapWidth) {
            position.x = 0 - shape.width;
        }

    }
}
