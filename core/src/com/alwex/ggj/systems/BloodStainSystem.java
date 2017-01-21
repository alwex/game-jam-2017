package com.alwex.ggj.systems;

import com.alwex.ggj.components.BloodStainComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by samsung on 21/01/2017.
 */
public class BloodStainSystem extends EntityProcessingSystem {

    ComponentMapper<BloodStainComponent> bloodStainMapper;

    ShapeRenderer shapeRenderer;
    OrthographicCamera camera;

    public BloodStainSystem(ShapeRenderer shapeRenderer, OrthographicCamera camera) {
        super(Aspect.all(BloodStainComponent.class));

        this.shapeRenderer = shapeRenderer;
        this.camera = camera;
    }

    @Override
    protected void begin() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    }

    @Override
    protected void process(Entity e) {
        BloodStainComponent bloodStain = bloodStainMapper.get(e);
        bloodStain.color.a -= 0.005f;
//        bloodStain.y -= 1f;
//        bloodStain.radius -= 0.1f;

        if (bloodStain.color.a > 0 && bloodStain.radius > 0) {
            shapeRenderer.setColor(bloodStain.color);
            shapeRenderer.circle(bloodStain.x, bloodStain.y, bloodStain.radius);
        } else {
            e.deleteFromWorld();
        }
    }

    @Override
    protected void end() {
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
