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
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by jbrungar on 21/01/17.
 */
@Wire
public class InputSystem extends EntityProcessingSystem implements InputProcessor {

    OrthographicCamera camera;
    SlicingSystem slicingSystem;

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
        if (!slicingSystem.isSlicing) {
            slicingSystem.isSlicing = true;
            slicingSystem.sliceStartTime = TimeUtils.millis();
            slicingSystem.sliceStart.x = camera.unproject(new Vector3(screenX, 0, 0)).x;
            slicingSystem.sliceStart.y = camera.unproject(new Vector3(0, screenY, 0)).y;
            slicingSystem.sliceEnd.x = camera.unproject(new Vector3(screenX, 0, 0)).x;
            slicingSystem.sliceEnd.y = camera.unproject(new Vector3(0, screenY, 0)).y;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (slicingSystem.isSlicing) {
            slicingSystem.isSlicing = false;
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        slicingSystem.sliceEnd.x = camera.unproject(new Vector3(screenX, 0, 0)).x;
        slicingSystem.sliceEnd.y = camera.unproject(new Vector3(0, screenY, 0)).y;
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
