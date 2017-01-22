package com.alwex.ggj.systems;

import com.alwex.ggj.events.SpawnEvent;
import com.alwex.ggj.events.ThrowFishEvent;
import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
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

        float minSpawn = 0.000001f;
        float maxSpawn = 0.005f;
        float minVelocity = 15;
        float maxVelocity = 7;
        float maxHeight = 0;
        float minHeight = Float.MAX_VALUE;
        float minX = 2;
        float maxX = 30;



        for(int i=0; i<velocities.length; i++){
            maxHeight = Math.max(maxHeight,velocities[i]);
            minHeight = Math.min(minHeight,velocities[i]);
        }

        float deltaHeight = maxHeight - minHeight;

        float invHeight = 2f/(float)Math.pow(deltaHeight,0.5f);

        for(float i=0; i<velocities.length; i+=invHeight) {
            if((int) i<velocities.length) {
                float x = floatMap(i, 0, velocities.length, minX, maxX);
                float y = velocities[(int) i] - 4f;
                float vy = floatMap(velocities[(int) i], minHeight, maxHeight, minVelocity, maxVelocity) * deltaHeight;
                float k = floatMap(velocities[(int) i], minHeight, maxHeight, minSpawn, maxSpawn);

                for (int j = 0; j < k; j++) {
                    float vx = MathUtils.random(-vy,vy);
                    fishSystem.spawn(x, y, vx, vy);
                }
            }
        }
    }
}
