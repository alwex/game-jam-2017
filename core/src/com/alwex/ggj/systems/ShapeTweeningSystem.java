package com.alwex.ggj.systems;

import aurelienribon.tweenengine.TweenManager;
import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

/**
 * Created by samsung on 21/01/2017.
 */
@Wire
public class ShapeTweeningSystem extends BaseSystem {

    EventSystem eventSystem;
    TweenManager tweenManager;

    public ShapeTweeningSystem(TweenManager tweenManager) {
        this.tweenManager = tweenManager;
    }

    @Override
    protected void processSystem() {

    }

    @Subscribe
    public void onSlice() {

    }
}
