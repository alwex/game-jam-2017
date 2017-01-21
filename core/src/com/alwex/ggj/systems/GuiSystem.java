package com.alwex.ggj.systems;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.alwex.ggj.components.GuiComponent;
import com.alwex.ggj.components.ScoreComponent;
import com.alwex.ggj.events.SlicedEvent;
import com.alwex.ggj.screens.LevelScreen;
import com.alwex.ggj.tween.accessors.LevelScreenAccessor;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import net.mostlyoriginal.api.event.common.Subscribe;

/**
 * Created by samsung on 21/01/2017.
 */
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
    }

    @Override
    protected void end() {
        batch.end();
    }

    @Subscribe
    public void onSlice(SlicedEvent event) {
        ScoreComponent score = scoreMapper.get(event.entityId);
        GuiComponent guiComponent = guiMapper.get(gui);
        guiComponent.score += score.value;

//        if ((guiComponent.combo + 1) % 20 == 0 && (bulletTime == null || bulletTime.isFinished())) {
//            bulletTime = Tween.to(levelScreen, LevelScreenAccessor.DELTA_FACTOR, 1f)
//                    .target(0.1f)
//                    .repeatYoyo(1, 3f)
//                    .ease(TweenEquations.easeInQuad)
//                    .start(tweenManager);
//        } else if(bulletTime == null || bulletTime.isFinished()) {
            guiComponent.combo += 1;
//        }
    }
}
