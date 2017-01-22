package com.alwex.ggj.systems;

import com.alwex.ggj.components.*;
import com.alwex.ggj.events.NoFishEvent;
import com.alwex.ggj.factory.EntityFactory;
import com.alwex.ggj.factory.FishDescriptor;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.utils.BagUtils;

import java.util.ArrayList;


/**
 * Created by jbrungar on 20/01/17.
 */
@Wire
public class FishSystem extends EntityProcessingSystem {

    EventSystem eventSystem;

    float mapWidth, mapHeight;
    ComponentMapper<PositionComponent> positionMapper;
    ComponentMapper<DeadComponent> deadMapper;
    OrthographicCamera camera;

    boolean askedForFish = false;

    ArrayList<FishDescriptor> fishDescriptors;

    public FishSystem(float mapWidth, float mapHeight, OrthographicCamera camera) {
        super(Aspect.all(FishComponent.class));

        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.camera = camera;
    }

    @Override
    protected void initialize() {
        fishDescriptors = new ArrayList<FishDescriptor>();
        fishDescriptors.add(new FishDescriptor("boot", 2, 2, 1));
        fishDescriptors.add(new FishDescriptor("bottle", 2, 2, 1));
        fishDescriptors.add(new FishDescriptor("crab", 2, 2, 10));
        fishDescriptors.add(new FishDescriptor("octopus4", 2,2, 15));
        fishDescriptors.add(new FishDescriptor("pengo", 2, 4, 20));
        fishDescriptors.add(new FishDescriptor("pengo", 2, 4, 20));
        fishDescriptors.add(new FishDescriptor("pengobaby", 2, 2, 100));
        fishDescriptors.add(new FishDescriptor("pengobaby", 2, 2, 100));
        fishDescriptors.add(new FishDescriptor("redfish1", 2, 2, 30));
        fishDescriptors.add(new FishDescriptor("redfish1", 2, 2, 30));
        fishDescriptors.add(new FishDescriptor("redfish2", 2, 2, 30));
        fishDescriptors.add(new FishDescriptor("redfish2", 2, 2, 30));
        fishDescriptors.add(new FishDescriptor("seal", 4, 4, 50));
        fishDescriptors.add(new FishDescriptor("whale", 20, 4, 1000));
        fishDescriptors.add(new FishDescriptor("whale", 10, 2, 1000));


    }

    @Override
    protected void begin() {
        if (getEntities().size() > 0) {
            askedForFish = false;
        } else {
            if(!askedForFish) {
                askedForFish = true;
                eventSystem.dispatch(new NoFishEvent());
            }
        }
    }

    @Override
    protected void process(Entity e) {
        PositionComponent pos = positionMapper.get(e);
        if (deadMapper.has(e)) {
//            pos.y -= 0.2f;
        } else {
//            pos.y += 0.1f;
        }

        if (pos.y < 0) {
                world.deleteEntity(e);
        }
    }
    public void spawn() {
        FishDescriptor currentDescriptor = fishDescriptors.get(MathUtils.random(0, fishDescriptors.size() - 1));
        Entity fish = EntityFactory.instance.createFish(
                world,
                currentDescriptor.name,
                currentDescriptor,
                MathUtils.random(0f, 32f),
                MathUtils.random(0f, 24f),
                MathUtils.random(-5, 5),
                MathUtils.random(30, 35)
        );

        fish.edit().add(new SliceableComponent());
    }

    public void spawn(float x, float y, float vx, float vy) {


        FishDescriptor currentDescriptor = fishDescriptors.get(MathUtils.random(0, fishDescriptors.size() - 1));
        Entity fish = EntityFactory.instance.createFish(
                world,
                currentDescriptor.name,
                currentDescriptor,
                x,
                y,
                vx,
                vy
        );

        fish.edit().add(new SliceableComponent());
    }

    public Vector2 centreOfMass() {
        float posX = camera.viewportWidth / 2;
        float posY = camera.viewportHeight / 2;

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
            if (fishY > mapHeight - camera.viewportHeight / 2) {
                posY = mapHeight - camera.viewportHeight / 2;
            } else if (fishY >= camera.viewportHeight / 2) {
                posY = fishY;
            }
        }

        return new Vector2(posX, posY);
    }
}
