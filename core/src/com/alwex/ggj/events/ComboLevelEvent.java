package com.alwex.ggj.events;

import net.mostlyoriginal.api.event.common.Event;

/**
 * Created by Isaac on 22/01/2017.
 */
public class ComboLevelEvent implements Event {
    public float level;
    public boolean degrade;
    public ComboLevelEvent(int level){
        this.level = level;
    }
    public ComboLevelEvent(int level,boolean degrade){
        this.level = level;
        this.degrade = degrade;
    }
}
