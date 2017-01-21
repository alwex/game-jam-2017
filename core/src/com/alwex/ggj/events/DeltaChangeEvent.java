package com.alwex.ggj.events;

import net.mostlyoriginal.api.event.common.Event;

/**
 * Created by Isaac on 22/01/2017.
 */
public class DeltaChangeEvent implements Event {
    public float target, resistance;
    public DeltaChangeEvent(float target){
        this.target = target;
        this.resistance = -1;
    }
    public DeltaChangeEvent(float target, float resistance){
        this.target = target;
        this.resistance = resistance;
    }
}
