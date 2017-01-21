package com.alwex.ggj.systems;

import com.alwex.ggj.components.PositionComponent;
import com.alwex.ggj.components.SpringComponent;
import com.alwex.ggj.events.SlicedEvent;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import net.mostlyoriginal.api.event.common.Subscribe;

import java.util.ArrayList;

/**
 * Created by samsung on 20/01/2017.
 */
@Wire
public class WaterRenderSystem extends BaseSystem {

    WaterSystem waterSystem;

    ShapeRenderer shapeRenderer;
    OrthographicCamera camera;

    ComponentMapper<SpringComponent> springMapper;
    ComponentMapper<PositionComponent> positionMapper;

    ArrayList<Entity> springList;

    private Mesh mesh;

    private ShaderProgram shaderWaterMesh;

    ImmediateModeRenderer20 meshRenderer;

    Color topColor;
    Color bottomColor;

    public WaterRenderSystem(ShapeRenderer shapeRenderer, OrthographicCamera camera) {
        super();
        this.shapeRenderer = shapeRenderer;
        this.camera = camera;
    }

    public void loadShader() {
        shaderWaterMesh = new ShaderProgram(
                Gdx.files.internal("shaders/passthrough.vert.glsl"),
                Gdx.files.internal("shaders/water-mesh.frag.glsl")
        );
        if (!shaderWaterMesh.isCompiled()) {
            Gdx.app.log("SHADER WATER MESHES", shaderWaterMesh.getLog());
        }
    }

    @Override
    protected void initialize() {
        meshRenderer = new ImmediateModeRenderer20(false, true, 0);

        topColor = new Color(0.46f, 0.65f, 0.74f, 0.9f);
        bottomColor = new Color(0.01f, 0.18f, 0.36f, 0.7f);

    }

    private void drawPolygon(float x1, float y1,
                             float x2, float y2,
                             float x3, float y3,
                             float x4, float y4,
                             Color c1, Color c2, Color c3, Color c4) {

        meshRenderer.color(c1.r, c1.g, c1.b, c1.a);
        meshRenderer.vertex(x1, y1, 0);

        meshRenderer.color(c2.r, c2.g, c2.b, c2.a);
        meshRenderer.vertex(x2, y2, 0);

        meshRenderer.color(c3.r, c3.g, c3.b, c3.a);
        meshRenderer.vertex(x3, y3, 0);


        meshRenderer.color(c1.r, c1.g, c1.b, c1.a);
        meshRenderer.vertex(x1, y1, 0);

        meshRenderer.color(c3.r, c3.g, c3.b, c3.a);
        meshRenderer.vertex(x3, y3, 0);

        meshRenderer.color(c4.r, c4.g, c4.b, c4.a);
        meshRenderer.vertex(x4, y4, 0);
    }

    @Override
    protected void begin() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        springList = waterSystem.getSpringList();
        meshRenderer.begin(camera.combined, GL20.GL_TRIANGLES);
    }

    @Override
    protected void processSystem() {

        float bottomLeftX = 0;
        float bottomLeftY = 0;

        float topLeftX = 0;
        float topLeftY = 0;

        float topRightX = 0;
        float topRightY = 0;

        float bottomRightX = 0;
        float bottomRightY = 0;

        for (int i = 0; i < springList.size(); i++) {
            Entity current = springList.get(i);

            PositionComponent currentPosition = positionMapper.get(current);

            bottomLeftX = currentPosition.x;
            bottomLeftY = 0;
            topLeftX = currentPosition.x;
            topLeftY = currentPosition.y;

            if (i < springList.size() - 1) {
                Entity rightEntity = springList.get(i + 1);
                PositionComponent rightPosition = positionMapper.get(rightEntity);
                topRightX = rightPosition.x;
                topRightY = rightPosition.y;
                bottomRightX = rightPosition.x;
                bottomRightY = 0;
            }

            drawPolygon(
                    bottomLeftX, bottomLeftY,
                    topLeftX, topLeftY,
                    topRightX, topRightY,
                    bottomRightX, bottomRightY,
                    bottomColor, topColor,
                    topColor, bottomColor
            );
        }
    }

    @Override
    protected void end() {
        meshRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Subscribe
    public void onSlice(SlicedEvent event) {
        float factor = 0.01f;

        topColor.r += (topColor.r < 0.8f) ? factor : 0;
        topColor.g -= (topColor.g > 0.1f) ? factor : 0;
        topColor.b -= (topColor.b > 0.1f) ? factor : 0;

        bottomColor.r += (bottomColor.r < 0.8f) ? factor : 0;
        bottomColor.g -= (bottomColor.g > 0.1f) ? factor : 0;
        bottomColor.b -= (bottomColor.b > 0.1f) ? factor : 0;
    }
}
