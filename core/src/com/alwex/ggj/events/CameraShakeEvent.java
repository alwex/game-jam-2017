package com.alwex.ggj.events;

import net.mostlyoriginal.api.event.common.Event;

/**
 * Created by jbrungar on 21/01/17.
 */
public class CameraShakeEvent implements Event {

    float intensity, duration;

    public CameraShakeEvent(float intensity, float duration) {
        this.intensity = intensity;
        this.duration = duration;
    }

    public float getIntensity() {
        return intensity;
    }

    public float getDuration() {
        return duration;
    }
}
