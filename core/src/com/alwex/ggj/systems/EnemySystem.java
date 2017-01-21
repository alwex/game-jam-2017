package com.alwex.ggj.systems;

import com.alwex.ggj.components.EnemyComponent;
import com.alwex.ggj.components.SliceableComponent;
import com.alwex.ggj.events.EnemySlicedEvent;
import com.alwex.ggj.events.SlicedEvent;
import com.artemis.Aspect;
import com.artemis.ComponentManager;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

/**
 * Created by Isaac on 22/01/2017.
 */
@Wire
public class EnemySystem extends EntityProcessingSystem {

    ComponentMapper<EnemyComponent> enemyMapper;

    EventSystem eventSystem;

    public EnemySystem() {
        super(Aspect.all(EnemyComponent.class, SliceableComponent.class));
    }

    @Override
    protected void process(Entity e) {

    }

    @Subscribe
    public void onSliced(SlicedEvent event) {
        EnemyComponent enemy = enemyMapper.get(event.entityId);
        if(enemy != null) {
            eventSystem.dispatch(new EnemySlicedEvent());
        }
    }
}
