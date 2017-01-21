package com.alwex.ggj.events;

import com.artemis.annotations.EntityId;
import net.mostlyoriginal.api.event.common.Event;

/**
 * Created by samsung on 21/01/2017.
 */
public class SlicedEvent implements Event {
    @EntityId
    public int entityId;

    public SlicedEvent(int entityId) {
        this.entityId = entityId;
    }
}
