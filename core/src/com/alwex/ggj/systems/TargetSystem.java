package com.alwex.ggj.systems;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by samsung on 22/01/2017.
 */
public class TargetSystem extends BaseSystem {

    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    OrthographicCamera camera;
    TextureAtlas atlas;

    public TargetSystem(ShapeRenderer shapeRenderer, SpriteBatch batch, OrthographicCamera camera, TextureAtlas atlas) {
        this.batch = batch;
        this.camera = camera;
        this.atlas = atlas;
        this.shapeRenderer = shapeRenderer;
    }

    @Override
    protected void processSystem() {
        float x = Gdx.input.getX() - Gdx.graphics.getWidth() / 2;
        float y = (Gdx.graphics.getHeight() - Gdx.input.getY()) - (Gdx.graphics.getHeight() / 2);
    }
}
