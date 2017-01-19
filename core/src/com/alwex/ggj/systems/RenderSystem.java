package com.alwex.ggj.systems;

import com.alwex.ggj.components.PositionComponent;
import com.alwex.ggj.components.SpriteComponent;
import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by alexandreguidet on 20/01/17.
 */
public class RenderSystem extends EntityProcessingSystem {

    SpriteBatch batch;
    OrthographicCamera camera;

    public RenderSystem(SpriteBatch batch, OrthographicCamera camera) {
        super(Aspect.all(PositionComponent.class, SpriteComponent.class));

        this.batch = batch;
        this.camera = camera;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void begin() {

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
    }

    @Override
    protected void process(Entity e) {

    }

    @Override
    protected void end() {
        batch.end();
    }
}
