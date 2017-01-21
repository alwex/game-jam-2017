package com.alwex.ggj.screens;

import com.alwex.ggj.JamGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by samsung on 22/01/2017.
 */
public class ScoreScreen implements Screen {

    public final JamGame game;
    BitmapFont font;
    int score, maxCombo;
    SpriteBatch batch;


    public ScoreScreen(JamGame game, int score) {
        this.game = game;
        this.score = score;
        this.maxCombo = maxCombo;
        this.batch = new SpriteBatch();

        font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"), false);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        font.draw(
                batch,
                "Your score : " + score,
                100,
                400
        );

        font.draw(
                batch,
                "Press Enter to kill again!",
                100,
                200
        );
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
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
        font.dispose();
        batch.dispose();
    }
}
