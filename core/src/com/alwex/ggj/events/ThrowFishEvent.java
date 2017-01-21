package com.alwex.ggj.events;

import net.mostlyoriginal.api.event.common.Event;

/**
 * Created by Isaac on 21/01/2017.
 */
public class ThrowFishEvent implements Event {

    public float[] velocityArray;
    public ThrowFishEvent(float[] velocityArray){
        this.velocityArray = velocityArray;
    }
}
