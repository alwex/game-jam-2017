package com.alwex.ggj.utils;

/**
 * Created by samsung on 22/01/2017.
 */
public class MyMaths {
    public static float  floatMap(float old_value, float old_bottom, float old_top, float new_bottom, float new_top){
        return (old_value - old_bottom) / (old_top - old_bottom) * (new_top - new_bottom) + new_bottom;
    }
}
