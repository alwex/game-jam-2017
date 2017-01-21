package com.alwex.ggj.systems;

import com.alwex.ggj.components.FishComponent;
import com.alwex.ggj.components.FocusComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by jbrungar on 21/01/17.
 */
@Wire
public class CameraSystem extends EntityProcessingSystem {

    OrthographicCamera camera;

    FishSystem fishSystem;

    public CameraSystem(OrthographicCamera camera) {
        super(Aspect.all(FocusComponent.class));

        this.camera = camera;
    }

    @Override
    public void initialize() {
        world.createEntity()
                .edit()
                .add(new FocusComponent())
                .getEntity();
    }

    @Override
    protected void process(Entity e) {
        Vector3 cameraPosition = camera.position;
        Vector2 focusPosition = fishSystem.centreOfMass();
        cameraPosition.y = new Vector2(cameraPosition.x, cameraPosition.y).lerp(focusPosition, 0.075f).y;
        camera.update();
    }
}
