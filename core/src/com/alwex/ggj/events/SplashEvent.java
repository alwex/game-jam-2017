package com.alwex.ggj.events;

import net.mostlyoriginal.api.event.common.Event;

/**
 * Created by Isaac on 21/01/2017.
 */
public class SplashEvent implements Event {
    public float x,y,mass;

    public SplashEvent (float x, float y,float mass) {
        this.x = x;
        this.y = y;
        this.mass = mass;
    }


}
