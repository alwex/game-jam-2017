package com.alwex.ggj.tween.accessors;

import aurelienribon.tweenengine.TweenAccessor;
import com.alwex.ggj.components.ShapeComponent;
import com.alwex.ggj.screens.LevelScreen;

/**
 * Created by samsung on 21/01/2017.
 */
public class LevelScreenAccessor implements TweenAccessor<LevelScreen> {
    public static final int DELTA_FACTOR = 1;

    @Override
    public int getValues(LevelScreen target, int tweenType, float[] returnValues) {

        switch (tweenType) {
            case DELTA_FACTOR:
                returnValues[0] = target.deltaFactor;
                return 1;
            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(LevelScreen target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case DELTA_FACTOR:
                target.deltaFactor = newValues[0];
                break;
            default:
                assert false;
        }
    }
}
