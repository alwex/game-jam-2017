package com.alwex.ggj.systems;

import com.alwex.ggj.events.SpawnEvent;
import com.alwex.ggj.events.ThrowFishEvent;
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

    private float  floatMap(float old_value, float old_bottom, float old_top, float new_bottom, float new_top){
        return (old_value - old_bottom) / (old_top - old_bottom) * (new_top - new_bottom) + new_bottom;
    }

    @Subscribe
    public void spawnEventListener(SpawnEvent event) {
        fishSystem.spawn();
    }

    @Subscribe
    public void throwFishEventListener(ThrowFishEvent event){
        float[] velocities = event.velocityArray;

        float minSpawn = 3;
        float maxSpawn = 10;
        float minVelocity = 50;
        float maxVelocity = 20;
        float maxHeight = 0;
        float minHeight = Float.MAX_VALUE;
        float minX = 2;
        float maxX = 30;



        for(int i=0; i<velocities.length; i++){
            maxHeight = Math.max(maxHeight,velocities[i]);
            minHeight = Math.min(minHeight,velocities[i]);
        }



        for(int i=0; i<velocities.length-1; i+=2) {
            float x = floatMap(i,0,velocities.length,minX,maxX);
            float y = velocities[i] - 4f;
            float vy = floatMap(velocities[i],minHeight,maxHeight,minVelocity,maxVelocity);
            float k = floatMap(velocities[i],minHeight,maxHeight,minSpawn,maxSpawn);
            for(int j=0; j<k; j++) {
                float vx = floatMap(j,0,k,-k,k);
                fishSystem.spawn(x, y, vx, vy);
            }
        }
    }
}
