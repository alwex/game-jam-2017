package com.alwex.ggj.systems;

import com.alwex.ggj.components.GuiComponent;
import com.alwex.ggj.components.ScoreComponent;
import com.alwex.ggj.events.SlicedEvent;
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

    ComponentMapper<GuiComponent> guiMapper;
    ComponentMapper<ScoreComponent> scoreMapper;

    public GuiSystem(SpriteBatch batch, OrthographicCamera camera) {
        super(Aspect.all(GuiComponent.class));

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
        guiComponent.combo += 1;
    }
}
