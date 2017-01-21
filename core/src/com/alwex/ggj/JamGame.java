package com.alwex.ggj;

import aurelienribon.tweenengine.TweenManager;
import com.alwex.ggj.screens.LevelScreen;
import com.alwex.ggj.systems.MicrophoneSystem;
import com.artemis.World;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class JamGame extends Game {

    SpriteBatch batch;
    Texture img;
    TweenManager tweenManager;
    AudioRecorder recorder;
    FPSLogger fpsLogger;

    AssetManager assetManager;

    public SpriteBatch getBatch() {
        return batch;
    }

    public TweenManager getTweenManager() {
        return tweenManager;
    }

    public AudioRecorder getRecorder() {
        return recorder;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    @Override
    public void create() {

        assetManager = new AssetManager();
        assetManager.load("sprites/atlas.atlas", TextureAtlas.class);
        assetManager.finishLoading();

        fpsLogger = new FPSLogger();
        recorder = Gdx.audio.newAudioRecorder(22050 * 4, true);
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        tweenManager = new TweenManager();
        this.setScreen(new LevelScreen(this, "template.tmx"));
    }

    @Override
    public void render() {
//	    tweenManager.update(Gdx.graphics.getDeltaTime());
        fpsLogger.log();
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        recorder.dispose();
        assetManager.dispose();
    }
}
