package com.alwex.ggj.systems;

import com.alwex.ggj.events.ComboLevelEvent;
import com.alwex.ggj.events.SlicedEvent;
import com.alwex.ggj.events.SplashEvent;
import com.alwex.ggj.events.ThrowFishEvent;
import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import net.mostlyoriginal.api.event.common.Subscribe;

/**
 * Created by samsung on 21/01/2017.
 */
@Wire
public class SoundSystem extends BaseSystem {

    AssetManager assetManager;
    Music ambient;
    Music music;
    DeltaSystem deltaSystem;
    private static String[][] comboLevelSoundVariants;

    Sound slicedSound = null;
    Sound thrownSound = null;
    Sound splashSound = null;

    long[] slicedSounds = new long[10];
    int slicedSoundsIndex = 0;
    long[] thrownSounds = new long[10];
    int thrownSoundsIndex = 0;
    long[] splashSounds = new long[10];
    int splashSoundsIndex = 0;
    private Sound comboLevelSound;
    long comboLevelSoundId = 0;

    public SoundSystem(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    protected void processSystem() {

        float delta = deltaSystem.getDeltaFactor();
        for(int i=0; i<10; i++){
            if(slicedSounds[i] != 0) {
                slicedSound.setPitch(slicedSounds[i], delta);
            }
            if(thrownSounds[i] != 0) {
                thrownSound.setPitch(thrownSounds[i], delta);
            }
            if(splashSounds[i] != 0) {
                splashSound.setPitch(splashSounds[i], delta);
            }
        }

        if(comboLevelSound!=null){
            comboLevelSound.setPitch(comboLevelSoundId, delta);
        }

    }

    @Override
    protected void initialize() {
        ambient = assetManager.get("sounds/ambient.ogg", Music.class);
        ambient.setLooping(true);
        ambient.play();

        music = assetManager.get("sounds/music.mp3", Music.class);
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();

        comboLevelSoundVariants = new String[5][];
        comboLevelSoundVariants[0] = new String[]{
                "sounds/nice_1.ogg",
                "sounds/nice_2.ogg",
                "sounds/nice_3.ogg",
                "sounds/nice_4.ogg",
                "sounds/nice_5.ogg"
        };
        comboLevelSoundVariants[1] = new String[]{
                "sounds/epic_1.ogg",
                "sounds/epic_2.ogg",
                "sounds/epic_3.ogg",
                "sounds/epic_4.ogg",
                "sounds/epic_5.ogg",
                "sounds/epic_6.ogg",
                "sounds/epic_7.ogg"
        };
        comboLevelSoundVariants[2] = new String[]{
                "sounds/unreal_1.ogg",
                "sounds/unreal_2.ogg",
                "sounds/unreal_3.ogg",
                "sounds/unreal_4.ogg",
                "sounds/unreal_5.ogg"
        };
        comboLevelSoundVariants[3] = new String[]{
                "sounds/warcrime_1.ogg",
                "sounds/warcrime_2.ogg",
                "sounds/warcrime_3.ogg",
                "sounds/warcrime_4.ogg",
                "sounds/warcrime_5.ogg",
                "sounds/warcrime_6.ogg"
        };
        comboLevelSoundVariants[4] = new String[]{
                "sounds/aaaaa_1.ogg",
               // "sounds/aaaaa_2.ogg",
               // "sounds/aaaaa_3.ogg",
               // "sounds/aaaaa_4.ogg",
               // "sounds/aaaaa_5.ogg",
               // "sounds/aaaaa_6.ogg",
                "sounds/aaaaa_7.ogg",
                "sounds/aaaaa_8.ogg",
                "sounds/aaaaa_9.ogg",
                "sounds/aaaaa_10.ogg",
                "sounds/aaaaa_11.ogg"
        };

        slicedSound = assetManager.get("sounds/splash.mp3", Sound.class);
        thrownSound = assetManager.get("sounds/water-splash.ogg", Sound.class);
        splashSound = assetManager.get("sounds/ploup.ogg", Sound.class);



    }


    @Subscribe
    public void onSliced(SlicedEvent event) {
        slicedSounds[slicedSoundsIndex] = slicedSound.play(0.1f, MathUtils.random(0.8f, 1f), 0);

        slicedSoundsIndex ++;
        if(slicedSoundsIndex > 9) {
            slicedSoundsIndex = 0;
        }
    }

    @Subscribe
    public void onThrowFish(ThrowFishEvent event) {
        thrownSounds[thrownSoundsIndex] = thrownSound.play(0.1f, MathUtils.random(0.8f, 1f), 0);

        thrownSoundsIndex ++;
        if(thrownSoundsIndex > 9) {
            thrownSoundsIndex = 0;
        }
    }

    @Subscribe
    public void onComboLevelReached(ComboLevelEvent event){
        if(comboLevelSound != null ){
            comboLevelSound.stop();
        }
        int index = Math.min((int)event.level, comboLevelSoundVariants.length - 1);
        String[] variants = comboLevelSoundVariants[index];
        int variant = MathUtils.random(0, variants.length - 1);

        comboLevelSound = assetManager.get(variants[variant], Sound.class);
        comboLevelSoundId = comboLevelSound.play(1,1,0);
    }


    @Subscribe
    public void onSplash(SplashEvent event) {
        splashSounds[splashSoundsIndex] = splashSound.play(0.1f, MathUtils.random(0.8f, 1f), 0);

        splashSoundsIndex ++;
        if(splashSoundsIndex > 9) {
            splashSoundsIndex = 0;
        }
    }
}
