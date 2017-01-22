package com.alwex.ggj.factory;

/**
 * Created by Isaac on 22/01/2017.
 */
public class EnemyDescriptor {
    public String name;
    public float width, height;
    public int score;

    public EnemyDescriptor(String name, float width, float height, int score) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.score = score;
    }
}
