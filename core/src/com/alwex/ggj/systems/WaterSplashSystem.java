package com.alwex.ggj.systems;

import com.alwex.ggj.components.PhysicComponent;
import com.alwex.ggj.components.PositionComponent;
import com.alwex.ggj.components.SplashComponent;
import com.alwex.ggj.events.SplashEvent;
import com.alwex.ggj.factory.EntityFactory;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;


/**
 * Created by Isaac on 21/01/2017.
 */
@Wire
public class WaterSplashSystem extends EntityProcessingSystem {

    WaterSystem waterSystem;
    EventSystem eventSystem;

    ComponentMapper<SplashComponent> splashMapper;
    ComponentMapper<PositionComponent> positionMapper;
    ComponentMapper<PhysicComponent> physicMapper;

    public WaterSplashSystem() {
        super(Aspect.all(SplashComponent.class, PositionComponent.class));
    }

    @Override
    protected void process(Entity e) {
        SplashComponent splashComponent = splashMapper.get(e);
        PositionComponent positionComponent = positionMapper.get(e);
        int i = MathUtils.floor(positionComponent.x );
        if(i < waterSystem.getSpringList().size() && i > 0) {

            PositionComponent springPosition = positionMapper.get(waterSystem.getSpringList().get(i));
            PhysicComponent physicComponent = physicMapper.get(e);

            if (springPosition.y > positionComponent.y && !splashComponent.inWater) {
                if(splashComponent.removeOnSplash){
                    e.deleteFromWorld();
                } else {
                    if (splashComponent.initialized)
                        eventSystem.dispatch(new SplashEvent(positionComponent.x, positionComponent.y, physicComponent.mass));
                    splashComponent.inWater = true;
                }
            }
            if (springPosition.y < positionComponent.y && splashComponent.inWater) {
                splashComponent.inWater = false;
            }
        }
        splashComponent.initialized = true;
    }

    @Subscribe
    public void splashEventListener(SplashEvent event) {
        Gdx.app.log("Water Splash",event.x+","+event.y);

        for(int i=0; i<event.mass*16f; i++) {

            EntityFactory.instance.createWaterSplash(
                    world,
                    event.x,
                    event.y,
                    MathUtils.random(-event.mass*4, event.mass*4),
                    MathUtils.random(event.mass*10f, event.mass*20f),
                    Color.BLACK
            );
        }

    }
}
