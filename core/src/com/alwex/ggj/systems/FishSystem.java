package com.alwex.ggj.systems;

import com.alwex.ggj.components.*;
import com.alwex.ggj.factory.EntityFactory;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import net.mostlyoriginal.api.utils.BagUtils;

import java.util.ArrayList;


/**
 * Created by jbrungar on 20/01/17.
 */
@Wire
public class FishSystem extends EntityProcessingSystem {

    float mapWidth, mapHeight;
    ComponentMapper<PositionComponent> positionMapper;
    ComponentMapper<DeadComponent> deadMapper;
    OrthographicCamera camera;

    ArrayList<String> fishNames;

    public FishSystem(float mapWidth, float mapHeight, OrthographicCamera camera) {
        super(Aspect.all(FishComponent.class));

        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.camera = camera;
    }

    @Override
    protected void initialize() {
        fishNames = new ArrayList<String>();
        fishNames.add("boot");
        fishNames.add("bottle");
        fishNames.add("crab");
        fishNames.add("divingman");
        fishNames.add("octopus2");
        fishNames.add("octopus3");
        fishNames.add("octopus4");
        fishNames.add("pengo");
        fishNames.add("pengobaby");
        fishNames.add("redfish1");
        fishNames.add("redfish2");
        fishNames.add("seal");
        fishNames.add("whale");


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
        EntityFactory.instance.createFish(
                world,
                fishNames.get(MathUtils.random(0, fishNames.size() - 1)),
                MathUtils.random(0f, 32f),
                MathUtils.random(0f, 24f),
                1, 1,
                MathUtils.random(1f, 2f),
                MathUtils.random(1f, 2f)
        );
    }

    public Vector2 centreOfMass() {
        float posX = camera.position.x;
        float posY = camera.position.y;

        Bag<Entity> fishEntities = this.getEntities();
        if (fishEntities.size() > 0) {
            Entity[] fishArray = fishEntities.getData();
            float sumX = 0;
            float sumY = 0;
            int count = 0;
            for (int i = 0; i < fishEntities.size(); i++) {
                if (!deadMapper.has(fishArray[i])) {
                    PositionComponent pos = positionMapper.get(fishArray[i]);
                    sumX += pos.x;
                    sumY += pos.y;
                    count++;
                }
            }
            float fishY = sumY / count;
            if (fishY >= camera.viewportHeight / 2 && fishY <= mapHeight - camera.viewportHeight / 2) {
                posY = fishY;
            }
        }

        return new Vector2(posX, posY);
    }
}
