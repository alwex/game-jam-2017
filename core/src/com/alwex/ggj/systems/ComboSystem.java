package com.alwex.ggj.systems;

import com.alwex.ggj.components.GuiComponent;
import com.alwex.ggj.events.ComboLevelEvent;
import com.alwex.ggj.events.SlicedEvent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.SystemInvocationStrategy;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

/**
 * Created by samsung on 21/01/2017.
 */
@Wire
public class ComboSystem extends EntityProcessingSystem {

    ComponentMapper<GuiComponent> guiMapper;
    long lastEvent = 0;
    EventSystem eventSystem;

    public ComboSystem() {
        super(Aspect.all(GuiComponent.class));
    }


    @Override
    protected void process(Entity e) {
        if (lastEvent + 3000l < System.currentTimeMillis()) {
            guiMapper.get(e).combo = 0;
            guiMapper.get(e).comboLevelReached = 0;
            eventSystem.dispatch(new ComboLevelEvent(0, true));

        }
    }

    @Subscribe
    public void onSlice(SlicedEvent event) {
        lastEvent = System.currentTimeMillis();
    }

}
