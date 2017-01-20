package com.alwex.ggj.systems;

import com.alwex.ggj.components.MicrophoneComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polyline;

/**
 * Created by Isaac on 21/01/2017.
 */
public class MicrophoneRenderSystem extends EntityProcessingSystem {

    ComponentMapper<MicrophoneComponent> microphoneMapper;
    OrthographicCamera camera;
    ShapeRenderer shapeRenderer;

    public MicrophoneRenderSystem( OrthographicCamera camera, ShapeRenderer shapeRenderer) {
        super(Aspect.all(MicrophoneComponent.class));

        this.camera = camera;
        this.shapeRenderer = shapeRenderer;
    }

    @Override
    protected void initialize() {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    protected void begin() {


        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    }
    @Override
    protected void process(Entity e) {
        MicrophoneComponent mic = microphoneMapper.get(e);

        Polyline line = mic.getLine();
        shapeRenderer.setColor(
                new Color(
                        ((float) mic.hz + 10.0f) / 50.0f,
                        ((float) mic.hz + 10.0f) / 50.0f,
                        ((float) mic.hz + 10.0f) / 50.0f,
                        1
                )
        );
        Polyline line2 = new Polyline(line.getTransformedVertices());

        line2.setPosition(0, 200f);
        Gdx.gl.glLineWidth(5);

        shapeRenderer.polyline(line2.getTransformedVertices());

    }
    protected void end() {
        shapeRenderer.end();
    }
}
