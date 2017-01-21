package com.alwex.ggj.tween.accessors;

import aurelienribon.tweenengine.TweenAccessor;
import com.alwex.ggj.components.ShapeComponent;

/**
 * Created by samsung on 21/01/2017.
 */
public class ShapeComponentAccessor implements TweenAccessor<ShapeComponent> {
    public static final int SCALE = 1;

    @Override
    public int getValues(ShapeComponent target, int tweenType, float[] returnValues) {

        switch (tweenType) {
            case SCALE:
                returnValues[0] = target.scale;
                return 1;
            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(ShapeComponent target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case SCALE:
                target.scale = newValues[0];
                break;
            default:
                assert false;
        }
    }
}
