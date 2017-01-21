package com.alwex.ggj.systems;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.alwex.ggj.components.GuiComponent;
import com.alwex.ggj.components.ScoreComponent;
import com.alwex.ggj.events.CameraShakeEvent;
import com.alwex.ggj.events.ComboLevelEvent;
import com.alwex.ggj.events.SlicedEvent;
import com.alwex.ggj.screens.LevelScreen;
import com.alwex.ggj.tween.accessors.LevelScreenAccessor;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

/**
 * Created by samsung on 21/01/2017.
 */
@Wire
public class GuiSystem extends EntityProcessingSystem {
    SpriteBatch batch;
    OrthographicCamera camera;
    BitmapFont font;
    Entity gui;

    LevelScreen levelScreen;
    TweenManager tweenManager;
    Tween bulletTime = null;

    ComponentMapper<GuiComponent> guiMapper;
    ComponentMapper<ScoreComponent> scoreMapper;

    EventSystem eventSystem;


    public GuiSystem(LevelScreen levelScreen, SpriteBatch batch, OrthographicCamera camera, TweenManager tweenManager) {
        super(Aspect.all(GuiComponent.class));

        this.levelScreen = levelScreen;
        this.tweenManager = tweenManager;
        this.batch = batch;
        this.camera = camera;
    }

    @Override
    protected void initialize() {
        font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"), false);

        gui = world.createEntity().edit()
                .add(new GuiComponent())
                .getEntity();
    }

    @Override
    protected void begin() {
        batch.setProjectionMatrix(camera.combined);
//        batch.setTransformMatrix(camera.projection);
        batch.begin();
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_1)) {
            eventSystem.dispatch(new ComboLevelEvent(0));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_2)) {
            eventSystem.dispatch(new ComboLevelEvent(1));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_3)) {
            eventSystem.dispatch(new ComboLevelEvent(2));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_4)) {
            eventSystem.dispatch(new ComboLevelEvent(3));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_5)) {
            eventSystem.dispatch(new ComboLevelEvent(4));
        }
    }

    @Override
    protected void process(Entity e) {
        GuiComponent gui = guiMapper.get(e);
        font.draw(
                batch,
                "Slaughter : " + gui.score,
                10 - (camera.viewportWidth / 2),
                (camera.viewportHeight - 10) - (camera.viewportHeight / 2)
        );

//        if (gui.combo > 0) {
        font.draw(
                batch,
                "Combo x " + gui.combo,
                10 - (camera.viewportWidth / 2),
                (camera.viewportHeight - 40) - (camera.viewportHeight / 2)
        );
//        }

        if (gui.combo > 5) {
//            levelScreen.game.showScore(gui.score, gui.combo);
        }
    }

    @Override
    protected void end() {
        batch.end();
    }

    @Subscribe
    public void onSlice(SlicedEvent event) {
        ScoreComponent score = scoreMapper.get(event.entityId);
        GuiComponent guiComponent = guiMapper.get(gui);
        guiComponent.score += score.value * guiComponent.combo + 1;
        int comboThreshold = 5;

//        if ((guiComponent.combo + 1) % 20 == 0 && (bulletTime == null || bulletTime.isFinished())) {
//            bulletTime = Tween.to(levelScreen, LevelScreenAccessor.DELTA_FACTOR, 1f)
//                    .target(0.1f)
//                    .repeatYoyo(1, 3f)
//                    .ease(TweenEquations.easeInQuad)
//                    .start(tweenManager);
//        } else if(bulletTime == null || bulletTime.isFinished()) {
        guiComponent.combo += 1;

        if(guiComponent.combo % comboThreshold == 0){
            if ( guiComponent.comboLevelReached < guiComponent.combo) {
                guiComponent.comboLevelReached = guiComponent.combo;
                eventSystem.dispatch(new ComboLevelEvent((int)(guiComponent.comboLevelReached / comboThreshold - 1)));
                eventSystem.dispatch(new CameraShakeEvent(0.25f*guiComponent.comboLevelReached / comboThreshold, 100f));
            } else {
                guiComponent.comboLevelReached = guiComponent.combo;
                eventSystem.dispatch(new ComboLevelEvent((int)(guiComponent.comboLevelReached / comboThreshold - 1), true));
            }
        }

//        }
    }
}
