package com.alwex.ggj.systems;

import com.alwex.ggj.components.PositionComponent;
import com.alwex.ggj.components.RotationComponent;
import com.alwex.ggj.components.ShapeComponent;
import com.alwex.ggj.components.SpriteComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by samsung on 21/01/2017.
 */
public class SpriteRenderSystem extends EntityProcessingSystem {

    ComponentMapper<PositionComponent> positionMapper;
    ComponentMapper<SpriteComponent> spriteMapper;
    ComponentMapper<ShapeComponent> shapeMapper;
    ComponentMapper<RotationComponent> rotationMapper;

    SpriteBatch batch;
    OrthographicCamera camera;
    TextureAtlas atlas;

    public SpriteRenderSystem(SpriteBatch batch, OrthographicCamera camera, TextureAtlas atlas) {
        super(Aspect.all(
                PositionComponent.class,
                SpriteComponent.class,
                ShapeComponent.class,
                RotationComponent.class
        ));

        this.batch = batch;
        this.camera = camera;
        this.atlas = atlas;
    }

    @Override
    protected void begin() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
    }

    @Override
    protected void process(Entity e) {
        PositionComponent position = positionMapper.get(e);
        SpriteComponent sprite = spriteMapper.get(e);
        ShapeComponent shape = shapeMapper.get(e);
        RotationComponent rotation = rotationMapper.get(e);

        batch.draw(
                atlas.findRegion(sprite.name),
                position.x, position.y,
                shape.width / 2f, shape.height / 2f,
                shape.width, shape.height,
                shape.scale, shape.scale,
                rotation.angle
        );
    }

    protected void end() {
        batch.end();
    }
}
