package com.alwex.ggj.systems;

import com.artemis.BaseSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by samsung on 21/01/2017.
 */
public class SkyRenderSystem extends BaseSystem {

    ShapeRenderer shapeRenderer;
    OrthographicCamera camera;

    Color topColor;
    Color bottomColor;

    float mapWidth;
    float mapHeight;

    public SkyRenderSystem(ShapeRenderer shapeRenderer, OrthographicCamera camera, float mapWidth, float mapHeight) {
        this.shapeRenderer = shapeRenderer;
        this.camera = camera;

        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }


    @Override
    protected void initialize() {
        topColor = new Color(0.30f, 0.62f, 0.71f, 1);
        bottomColor = new Color(0.75f, 0.83f, 0.80f, 1);
    }

    @Override
    protected void processSystem() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0, 0, mapWidth, mapHeight, bottomColor, bottomColor, topColor, topColor);
        shapeRenderer.end();
    }
}
