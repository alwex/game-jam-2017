package com.alwex.ggj.systems;

import com.alwex.ggj.components.PositionComponent;
import com.alwex.ggj.components.ShapeComponent;
import com.alwex.ggj.components.SpriteComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by alexandreguidet on 20/01/17.
 */
@Wire
public class RenderSystem extends EntityProcessingSystem {

    ComponentMapper<PositionComponent> positionMapper;
    ComponentMapper<ShapeComponent> shapeMapper;


    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    OrthographicCamera camera;

    public RenderSystem(SpriteBatch batch, OrthographicCamera camera) {
        super(Aspect.all(PositionComponent.class, ShapeComponent.class));

        this.batch = batch;
        this.camera = camera;
    }

    @Override
    protected void initialize() {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    protected void begin() {

//        batch.setProjectionMatrix(camera.combined);
//        batch.begin();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.YELLOW);
    }

    @Override
    protected void process(Entity e) {

        PositionComponent p = positionMapper.get(e);
        ShapeComponent shape = shapeMapper.get(e);

        shapeRenderer.rect(p.x, p.y, shape.width, shape.height);
    }

    @Override
    protected void end() {
//        batch.end();
        shapeRenderer.end();
    }
}
