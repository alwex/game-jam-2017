package com.alwex.ggj.events;

import net.mostlyoriginal.api.event.common.Event;

/**
 * Created by Isaac on 22/01/2017.
 */
public class GameOverEvent implements Event {
    public int score;
    public GameOverEvent(int score){
        this.score = score;
    }
}
