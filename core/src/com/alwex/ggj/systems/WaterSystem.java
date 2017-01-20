package com.alwex.ggj.systems;

import com.alwex.ggj.components.PositionComponent;
import com.alwex.ggj.components.ShapeComponent;
import com.alwex.ggj.components.SpringComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

/**
 * Created by samsung on 20/01/2017.
 */
@Wire
public class WaterSystem extends EntityProcessingSystem {

    MapSystem mapSystem;
    float mapWidth, mapHeight;
    float k = 0.025f;
    float spread = 0.01f;
    float dampening = 0.05f;
    float waterLevel = 15f;

    Entity target;

    ComponentMapper<SpringComponent> springMapper;
    ComponentMapper<PositionComponent> positionMapper;

    ArrayList<Entity> springList;

    public WaterSystem(float mapWidth, float mapHeight) {
        super(Aspect.all(SpringComponent.class));

        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    public ArrayList<Entity> getSpringList() {
        return springList;
    }

    @Override
    protected void initialize() {
        springList = new ArrayList<Entity>();

        float resolution = 1f;

        for (int i = 0; i <= mapWidth * (1 / resolution); i++) {
            Entity e = world.createEntity()
                    .edit()
                    .add(new PositionComponent(i * resolution, waterLevel))
                    .add(new ShapeComponent(1 * resolution, 1 * resolution))
                    .add(new SpringComponent(0, 0, waterLevel, 0, 0))
                    .getEntity();
            springList.add(e);

            if (i == (mapWidth * (1 / resolution)) / 2f) {
                target = e;
            }
        }

        for (int i = 0; i < springList.size(); i++) {
            Entity currentEntity = springList.get(i);
            SpringComponent spring = springMapper.get(currentEntity);

            if (i > 0) {
                int previousEntity = springList.get(i - 1).getId();
                spring.leftEntity = previousEntity;
            }

            if (i < springList.size() - 1) {
                int nextEntityId = springList.get(i + 1).getId();
                spring.rightEntity = nextEntityId;
            }
        }
    }

    @Override
    protected void begin() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            PositionComponent p = positionMapper.get(target);
            p.y = 4;
        }
    }

    @Override
    protected void process(Entity e) {
        SpringComponent s = springMapper.get(e);
        PositionComponent p = positionMapper.get(e);

        float x = p.y - s.startY;
        float acceleration = (-k * x) - (dampening * s.v);
        p.y += s.v;
        s.v += acceleration;
    }

    @Override
    protected void end() {
        float[] leftDeltas = new float[springList.size()];
        float[] rightDeltas = new float[springList.size()];

        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < springList.size(); i++) {
                if (i > 0) {

                    SpringComponent springCurrent = springMapper.get(springList.get(i));
                    SpringComponent springLeft = springMapper.get(springList.get(i - 1));

                    PositionComponent positionCurrent = positionMapper.get(springList.get(i));
                    PositionComponent positionLeft = positionMapper.get(springList.get(i - 1));

                    leftDeltas[i] = spread * (positionCurrent.y - positionLeft.y);
                    springLeft.v += leftDeltas[i];
                }
                if (i < springList.size() - 1) {
                    SpringComponent springCurrent = springMapper.get(springList.get(i));
                    SpringComponent springRight = springMapper.get(springList.get(i + 1));

                    PositionComponent positionCurrent = positionMapper.get(springList.get(i));
                    PositionComponent positionRight = positionMapper.get(springList.get(i + 1));

                    rightDeltas[i] = spread * (positionCurrent.y - positionRight.y);
                    springRight.v += rightDeltas[i];
                }
            }

            for (int i = 0; i < springList.size(); i++) {
                if (i > 0) {
                    SpringComponent springCurrent = springMapper.get(springList.get(i));
                    SpringComponent springLeft = springMapper.get(springList.get(i - 1));

                    PositionComponent positionCurrent = positionMapper.get(springList.get(i));
                    PositionComponent positionLeft = positionMapper.get(springList.get(i - 1));
                    positionLeft.y += leftDeltas[i];
                }
                if (i < springList.size() - 1) {
                    SpringComponent springCurrent = springMapper.get(springList.get(i));
                    SpringComponent springRight = springMapper.get(springList.get(i + 1));

                    PositionComponent positionCurrent = positionMapper.get(springList.get(i));
                    PositionComponent positionRight = positionMapper.get(springList.get(i + 1));

                    positionRight.y += rightDeltas[i];
                }
            }
        }
    }
}
