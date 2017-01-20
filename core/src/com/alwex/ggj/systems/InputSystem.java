package com.alwex.ggj.systems;

import com.alwex.ggj.components.SliceableComponent;
import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by jbrungar on 21/01/17.
 */
@Wire
public class InputSystem extends EntityProcessingSystem implements InputProcessor {

    OrthographicCamera camera;
    SliceableSystem sliceableSystem;

    public InputSystem(OrthographicCamera camera) {
        super(Aspect.all(SliceableComponent.class));

        this.camera = camera;
    }

    @Override
    protected void initialize() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    protected void process(Entity e) {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (!sliceableSystem.isSlicing) {
            sliceableSystem.isSlicing = true;
            sliceableSystem.sliceStart.x = camera.unproject(new Vector3(screenX, 0, 0)).x;
            sliceableSystem.sliceStart.y = camera.unproject(new Vector3(0, screenY, 0)).y;
            sliceableSystem.sliceEnd.x = camera.unproject(new Vector3(screenX, 0, 0)).x;
            sliceableSystem.sliceEnd.y = camera.unproject(new Vector3(0, screenY, 0)).y;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (sliceableSystem.isSlicing) {
            sliceableSystem.isSlicing = false;
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        sliceableSystem.sliceEnd.x = camera.unproject(new Vector3(screenX, 0, 0)).x;
        sliceableSystem.sliceEnd.y = camera.unproject(new Vector3(0, screenY, 0)).y;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
