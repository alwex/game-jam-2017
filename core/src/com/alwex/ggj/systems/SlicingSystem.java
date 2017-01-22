package com.alwex.ggj.systems;

import com.alwex.ggj.components.SliceLineComponent;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by jbrungar on 22/01/17.
 */
public class SlicingSystem extends BaseSystem {

    boolean isSlicing;
    public Vector2 sliceStart;
    public Vector2 sliceEnd;
    public long sliceStartTime;

    public Entity currentSliceLine;

    ComponentMapper<SliceLineComponent> sliceLineMapper;

    @Override
    protected void initialize() {
        isSlicing = false;
        sliceStart = new Vector2();
        sliceEnd = new Vector2();
        sliceStartTime = 0;
    }

    @Override
    protected void processSystem() {
        if (isSlicing()) {
            if (currentSliceLine == null) {
                currentSliceLine = world.createEntity().edit()
                        .add(new SliceLineComponent(sliceStart, sliceEnd))
                        .getEntity();
            } else {
                SliceLineComponent slice = sliceLineMapper.get(currentSliceLine);
                if (slice != null) {
                    slice.sliceEnd = sliceEnd;
                }
            }
        } else {
            currentSliceLine = null;
        }
    }

    public boolean isSlicing() {
        if (isSlicing && TimeUtils.timeSinceMillis(sliceStartTime) < 200) {
            return true;
        }
        return false;
    }
}
