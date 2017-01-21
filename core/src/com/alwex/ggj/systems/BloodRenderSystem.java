package com.alwex.ggj.systems;

import com.alwex.ggj.components.BloodDropComponent;
import com.alwex.ggj.components.PositionComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by samsung on 21/01/2017.
 */
@Wire
public class BloodRenderSystem extends EntityProcessingSystem {

    ShapeRenderer shapeRenderer;
    OrthographicCamera camera;

    ComponentMapper<PositionComponent> positionMapper;
    ComponentMapper<BloodDropComponent> bloodMapper;


    public BloodRenderSystem(ShapeRenderer shapeRenderer, OrthographicCamera camera) {
        super(Aspect.all(BloodDropComponent.class, PositionComponent.class));

        this.shapeRenderer = shapeRenderer;
        this.camera = camera;
    }

    protected void begin() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    }

    @Override
    protected void process(Entity e) {
        PositionComponent position = positionMapper.get(e);
        BloodDropComponent blood = bloodMapper.get(e);

        shapeRenderer.setColor(blood.color);
        shapeRenderer.circle(position.x, position.y, blood.width, 8);
    }

    protected void end() {
        shapeRenderer.end();
    }
}
