package com.alwex.ggj.systems;

import com.artemis.Aspect;
import com.artemis.annotations.Wire;
import com.artemis.systems.IntervalEntitySystem;

/**
 * Created by jbrungar on 21/01/17.
 */
@Wire
public class SpawnSystem extends IntervalEntitySystem {

    FishSystem fishSystem;

    public SpawnSystem(float interval) {
        super(Aspect.all(), interval);
    }

    @Override
    protected void processSystem() {
        fishSystem.spawn();
    }
}
