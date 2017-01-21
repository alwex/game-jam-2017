package com.alwex.ggj.systems;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.alwex.ggj.components.ShapeComponent;
import com.alwex.ggj.events.SlicedEvent;
import com.alwex.ggj.tween.accessors.ShapeComponentAccessor;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

/**
 * Created by samsung on 21/01/2017.
 */
@Wire
public class ShapeTweeningSystem extends BaseSystem {

    ComponentMapper<ShapeComponent> shapeMapper;

    EventSystem eventSystem;
    TweenManager tweenManager;

    public ShapeTweeningSystem(TweenManager tweenManager) {
        this.tweenManager = tweenManager;
    }

    @Override
    protected void processSystem() {

    }

    @Subscribe
    public void onSlice(SlicedEvent event) {
        ShapeComponent shape = shapeMapper.get(event.entityId);

        Tween.to(shape, ShapeComponentAccessor.SCALE, 0.25f)
                .target(1.5f)
                .repeatYoyo(5, 0)
                .ease(TweenEquations.easeInOutBounce)
                .start(tweenManager);
    }
}
