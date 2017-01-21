package com.alwex.ggj.systems;

import com.alwex.ggj.components.BleedingComponent;
import com.alwex.ggj.components.PositionComponent;
import com.artemis.Aspect;
import com.artemis.EntitySystem;

import javax.swing.text.Position;

/**
 * Created by samsung on 21/01/2017.
 */
public class BleedingSystem extends EntitySystem {
    public BleedingSystem() {
        super(Aspect.all(PositionComponent.class, BleedingComponent.class));
    }

    @Override
    protected void processSystem() {

    }
}
