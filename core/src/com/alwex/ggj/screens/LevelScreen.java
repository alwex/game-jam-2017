package com.alwex.ggj.screens;

import com.alwex.ggj.JamGame;
import com.alwex.ggj.components.PositionComponent;
import com.alwex.ggj.components.ShapeComponent;
import com.alwex.ggj.systems.MapSystem;
import com.alwex.ggj.systems.PositionSystem;
import com.alwex.ggj.systems.RenderSystem;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.system.render.MapRenderSystem;

/**
 * Created by alexandreguidet on 20/01/17.
 */
public class LevelScreen implements Screen {

    final JamGame game;

    OrthogonalTiledMapRenderer mapRenderer;
    OrthographicCamera camera;
    SpriteBatch batch;
    TiledMap map;
    World world;

    public LevelScreen(final JamGame game, String mapName) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 32, 24);

        batch = game.getBatch();

        map = new TmxMapLoader().load("maps/" + mapName);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 8f);

        WorldConfiguration config = new WorldConfigurationBuilder()
                .with(
                        new EventSystem(),

                        // other systems goes here
                        new MapSystem(mapRenderer, camera),
                        new RenderSystem(batch, camera),
                        new PositionSystem()
                ).build();

        world = new World(config);
    }

    @Override
    public void show() {
        Entity e = world.createEntity()
                .edit()
                .add(new PositionComponent(10, 10))
                .add(new ShapeComponent(10, 10))
                .getEntity();

        Entity e2 = world.createEntity()
                .edit()
                .add(new ShapeComponent(100, 100))
                .add(new PositionComponent(100, 300))
                .getEntity();
    }

    @Override
    public void render(float delta) {

        // clear the screen with plain black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.setDelta(delta);
        world.process();

        batch.begin();
//        batch.draw(img, 0, 0);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();
    }
}
