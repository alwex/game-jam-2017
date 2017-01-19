package com.alwex.ggj;

import aurelienribon.tweenengine.TweenManager;
import com.alwex.ggj.screens.LevelScreen;
import com.artemis.World;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class JamGame extends Game {

	SpriteBatch batch;
	Texture img;
	TweenManager tweenManager;

    public SpriteBatch getBatch() {
        return batch;
    }

    public TweenManager getTweenManager() {
        return tweenManager;
    }

    @Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		tweenManager = new TweenManager();

		this.setScreen(new LevelScreen(this));
	}

	@Override
	public void render () {

	    tweenManager.update(Gdx.graphics.getDeltaTime());
	    super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
