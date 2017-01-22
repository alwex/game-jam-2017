package com.alwex.ggj.screens;

import com.alwex.ggj.JamGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by samsung on 22/01/2017.
 */
public class SplashScreen implements Screen {

    public final JamGame game;
    SpriteBatch batch;
    Texture splash;
    long startedAt = 0;


    public SplashScreen(JamGame game) {
        this.game = game;
        this.batch = new SpriteBatch();

        splash = new Texture(Gdx.files.internal("images/splash.jpg"));

        startedAt = System.currentTimeMillis();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        batch.begin();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.draw(splash, 100, 0);
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            game.restartLevel();
        }
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
        batch.dispose();
        splash.dispose();
    }
}
