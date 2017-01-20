package com.alwex.ggj.systems;

import com.alwex.ggj.components.PositionComponent;
import com.alwex.ggj.components.SpringComponent;
import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

/**
 * Created by samsung on 20/01/2017.
 */
@Wire
public class WaterRenderSystem extends BaseSystem {

    WaterSystem waterSystem;

    ShapeRenderer shapeRenderer;
    OrthographicCamera camera;

    ComponentMapper<SpringComponent> springMapper;
    ComponentMapper<PositionComponent> positionMapper;

    ArrayList<Entity> springList;

    public WaterRenderSystem(ShapeRenderer shapeRenderer, OrthographicCamera camera) {
        super();
        this.shapeRenderer = shapeRenderer;
        this.camera = camera;
    }

    @Override
    protected void begin() {
        springList = waterSystem.getSpringList();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLUE);
    }

    @Override
    protected void processSystem() {

        for (int i = 0; i < springList.size(); i++) {

            

            if (i > 0) {
                Entity leftEntity = springList.get(i - 1);
            }

            if (i < springList.size() - 1) {
                Entity rightEntity = springList.get(i - 1);
            }
        }

        //SpringComponent spring = springMapper.get();
        //PositionComponent position = positionMapper.get(e);
    }

    @Override
    protected void end() {
        shapeRenderer.end();
    }
}
