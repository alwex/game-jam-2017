package com.alwex.ggj;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.alwex.ggj.components.ShapeComponent;
import com.alwex.ggj.screens.LevelScreen;
import com.alwex.ggj.systems.MicrophoneSystem;
import com.alwex.ggj.tween.accessors.LevelScreenAccessor;
import com.alwex.ggj.tween.accessors.ShapeComponentAccessor;
import com.artemis.World;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class JamGame extends Game {

    SpriteBatch batch;
    SpriteBatch staticBatch;
    Texture img;
    TweenManager tweenManager;
    AudioRecorder recorder;
    FPSLogger fpsLogger;

    AssetManager assetManager;

    public SpriteBatch getStaticBatch() {
        return staticBatch;
    }

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
        assetManager.load("sounds/splash.mp3", Sound.class);
        assetManager.load("sounds/water-splash.ogg", Sound.class);
        assetManager.load("sounds/ambient.ogg", Music.class);
        assetManager.load("sounds/music.mp3", Music.class);
        assetManager.load("sounds/ploup.ogg", Sound.class);


        assetManager.load("sounds/nice_1.ogg", Sound.class);
        assetManager.load("sounds/nice_2.ogg", Sound.class);
        assetManager.load("sounds/nice_3.ogg", Sound.class);
        assetManager.load("sounds/nice_4.ogg", Sound.class);
        assetManager.load("sounds/nice_5.ogg", Sound.class);
        assetManager.load("sounds/epic_1.ogg", Sound.class);
        assetManager.load("sounds/epic_2.ogg", Sound.class);
        assetManager.load("sounds/epic_3.ogg", Sound.class);
        assetManager.load("sounds/epic_4.ogg", Sound.class);
        assetManager.load("sounds/epic_5.ogg", Sound.class);
        assetManager.load("sounds/epic_6.ogg", Sound.class);
        assetManager.load("sounds/epic_7.ogg", Sound.class);
        assetManager.load("sounds/unreal_1.ogg", Sound.class);
        assetManager.load("sounds/unreal_2.ogg", Sound.class);
        assetManager.load("sounds/unreal_3.ogg", Sound.class);
        assetManager.load("sounds/unreal_4.ogg", Sound.class);
        assetManager.load("sounds/unreal_5.ogg", Sound.class);
        assetManager.load("sounds/warcrime_1.ogg", Sound.class);
        assetManager.load("sounds/warcrime_2.ogg", Sound.class);
        assetManager.load("sounds/warcrime_3.ogg", Sound.class);
        assetManager.load("sounds/warcrime_4.ogg", Sound.class);
        assetManager.load("sounds/warcrime_5.ogg", Sound.class);
        assetManager.load("sounds/warcrime_6.ogg", Sound.class);
        assetManager.load("sounds/aaaaa_1.ogg", Sound.class);
        //assetManager.load("sounds/aaaaa_2.ogg", Sound.class);
        //assetManager.load("sounds/aaaaa_3.ogg", Sound.class);
        //assetManager.load("sounds/aaaaa_4.ogg", Sound.class);
        //assetManager.load("sounds/aaaaa_5.ogg", Sound.class);
        //assetManager.load("sounds/aaaaa_6.ogg", Sound.class);
        assetManager.load("sounds/aaaaa_7.ogg", Sound.class);
        assetManager.load("sounds/aaaaa_8.ogg", Sound.class);
        assetManager.load("sounds/aaaaa_9.ogg", Sound.class);
        assetManager.load("sounds/aaaaa_10.ogg", Sound.class);
        assetManager.load("sounds/aaaaa_11.ogg",Sound.class);


        assetManager.finishLoading();

        fpsLogger = new FPSLogger();
        recorder = Gdx.audio.newAudioRecorder(22050 * 4, true);
        batch = new SpriteBatch();
        staticBatch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        tweenManager = new TweenManager();
        Tween.registerAccessor(ShapeComponent.class, new ShapeComponentAccessor());
        Tween.registerAccessor(LevelScreen.class, new LevelScreenAccessor());

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
        staticBatch.dispose();
        img.dispose();
        recorder.dispose();
        assetManager.dispose();
    }
}
