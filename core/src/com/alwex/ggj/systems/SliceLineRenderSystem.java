package com.alwex.ggj.systems;

import com.alwex.ggj.components.SliceLineComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by jbrungar on 21/01/17.
 */
@Wire
public class SliceLineRenderSystem extends EntityProcessingSystem {

    ComponentMapper<SliceLineComponent> sliceLineMapper;

    ShapeRenderer shapeRenderer;
    Camera camera;

    public SliceLineRenderSystem(ShapeRenderer shapeRenderer, Camera camera) {
        super(Aspect.all(SliceLineComponent.class));

        this.shapeRenderer = shapeRenderer;
        this.camera = camera;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void begin() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    }

    @Override
    protected void process(Entity e) {
        SliceLineComponent slice = sliceLineMapper.get(e);
        slice.color.a -= 0.1f;
        if (slice.color.a > 0) {
            shapeRenderer.setColor(slice.color);
            shapeRenderer.rectLine(slice.sliceStart, slice.sliceEnd, 0.25f);
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
