package com.alwex.ggj.systems;

import com.alwex.ggj.events.SpawnEvent;
import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

/**
 * Created by jbrungar on 21/01/17.
 */
@Wire
public class SpawnSystem extends BaseSystem {

    EventSystem eventSystem;
    FishSystem fishSystem;

    public SpawnSystem() {
    }

    @Override
    protected void processSystem() {
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            eventSystem.dispatch(new SpawnEvent());
        }
    }

    @Subscribe
    public void spawnEventListener(SpawnEvent event) {
        fishSystem.spawn();
    }
}
