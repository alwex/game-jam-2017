package com.alwex.ggj.systems;

import com.alwex.ggj.events.DeltaChangeEvent;
import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

/**
 * Created by Isaac on 22/01/2017.
 */
@Wire
public class DeltaSystem extends BaseSystem {

    private float currentDeltaFactor = 1;
    private float targetDeltaFactor = 1;
    private float resistance = 10;

    public float getDeltaFactor(){
        return currentDeltaFactor;
    }

    public float getDelta(){
        return currentDeltaFactor * world.getDelta();

    }

    EventSystem eventSystem;

    @Subscribe
    public void changeDeltaSpeedListener(DeltaChangeEvent event){
        this.targetDeltaFactor = event.target;
        if(event.resistance >= 0) {
            this.resistance = event.resistance;
        }
    }


    @Override
    protected void processSystem() {

        float rawDelta = world.getDelta();
        int framesToProcess = (int)Math.round(0.06/rawDelta);

        if(targetDeltaFactor < 0) {
            targetDeltaFactor = 0;
        }



        for(int i=0; i<framesToProcess; i++) {
            currentDeltaFactor = (currentDeltaFactor * resistance  + targetDeltaFactor) / (resistance + 1);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)) {
            targetDeltaFactor += 0.1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN)) {
            targetDeltaFactor -= 0.1;
        }
    }
}
