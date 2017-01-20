package com.alwex.ggj.systems;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Created by samsung on 15/01/2017.
 */
@Wire
public class MapSystem extends BaseSystem {

    OrthographicCamera camera;
    OrthogonalTiledMapRenderer mapRenderer;

    public MapSystem(OrthogonalTiledMapRenderer mapRenderer, OrthographicCamera camera) {
        this.camera = camera;
        this.mapRenderer = mapRenderer;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void processSystem() {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }
}
