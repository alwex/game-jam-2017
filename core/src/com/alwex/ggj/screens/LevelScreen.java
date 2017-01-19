package com.alwex.ggj.screens;

import com.alwex.ggj.JamGame;
import com.alwex.ggj.systems.RenderSystem;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import net.mostlyoriginal.api.event.common.EventSystem;

/**
 * Created by alexandreguidet on 20/01/17.
 */
public class LevelScreen implements Screen {

    final JamGame game;
    OrthographicCamera camera;
    SpriteBatch batch;
    World world;

    public LevelScreen(final JamGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        batch = game.getBatch();

        WorldConfiguration config = new WorldConfigurationBuilder()
                .with(
                        new EventSystem(),

                        // other systems goes here
                        new RenderSystem(batch, camera)
                ).build();

        world = new World(config);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        world.setDelta(delta);
        world.process();

        Gdx.gl.glClearColor(1, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
