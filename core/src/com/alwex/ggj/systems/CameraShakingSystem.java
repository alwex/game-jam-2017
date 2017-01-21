package com.alwex.ggj.systems;

import com.alwex.ggj.events.CameraShakeEvent;
import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

/**
 * Created by jbrungar on 21/01/17.
 */
@Wire
public class CameraShakingSystem extends BaseSystem {

    EventSystem eventSystem;
    DeltaSystem deltaSystem;

    OrthographicCamera camera;
    float intensity;
    float duration;
    float elapsed;
    float maxIntensity = 5f;
    float maxDuration = 2f;

    public CameraShakingSystem(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    protected void initialize() {
        intensity = 0;
        duration = 0;
        elapsed = 0;
    }

    @Override
    protected void processSystem() {
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            eventSystem.dispatch(new CameraShakeEvent(0.25f, 100f));
        }

        if (elapsed < duration) {
            float power = intensity * ((duration - elapsed) / duration);
            float x = MathUtils.random(-0.5f, 0.5f) * power;
            camera.position.x = camera.viewportWidth / 2 + x;
            elapsed += deltaSystem.getDelta();
        } else {
            intensity = 0;
            duration = 0;
            elapsed = 0;
            camera.position.x = camera.viewportWidth / 2;
        }
    }

    @Subscribe
    public void cameraShakeEventListener(CameraShakeEvent event) {
        intensity += event.getIntensity();
        if (intensity > maxIntensity) {
            intensity = maxIntensity;
        }
        duration += event.getDuration() / 1000f;
        if (duration > maxDuration) {
            duration = maxDuration;
        }
    }
}
