package com.alwex.ggj.systems;

import com.alwex.ggj.components.SpringComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by samsung on 20/01/2017.
 */
public class WaterRenderSystem extends EntityProcessingSystem {

    ShapeRenderer shapeRenderer;
    OrthographicCamera camera;

    ComponentMapper<SpringComponent> springMapper;

    public WaterRenderSystem(ShapeRenderer shapeRenderer, OrthographicCamera camera) {
        super(Aspect.all(SpringComponent.class));
    }

    @Override
    protected void begin() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLUE);
    }

    @Override
    protected void process(Entity e) {
        SpringComponent spring = springMapper.get(e);
    }

    @Override
    protected void end() {
        shapeRenderer.end();
    }
}
