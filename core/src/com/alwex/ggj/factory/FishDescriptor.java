package com.alwex.ggj.factory;

/**
 * Created by samsung on 21/01/2017.
 */
public class FishDescriptor {
    public String name;
    public float width, height;
    public int score;

    public FishDescriptor(String name, float width, float height, int score) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.score = score;
    }
}
