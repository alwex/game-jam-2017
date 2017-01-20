package com.alwex.ggj.systems;

import com.alwex.ggj.components.DeadComponent;
import com.alwex.ggj.components.PositionComponent;
import com.alwex.ggj.components.ShapeComponent;
import com.alwex.ggj.components.SliceableComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by jbrungar on 20/01/17.
 */
public class SliceableSystem extends EntityProcessingSystem {

    ComponentMapper<PositionComponent> positionMapper;
    ComponentMapper<ShapeComponent> shapeMapper;
    ComponentMapper<DeadComponent> deadMapper;
    ComponentMapper<SliceableComponent> sliceableMapper;


    public boolean isSlicing;
    public Vector2 sliceStart;
    public Vector2 sliceEnd;

    public SliceableSystem() {
        super(Aspect.all(SliceableComponent.class));
    }

    @Override
    protected void initialize() {
        isSlicing = false;
        sliceStart = new Vector2();
        sliceEnd = new Vector2();
    }

    @Override
    protected void process(Entity e) {
        if (isSlicing) {
            PositionComponent pos = positionMapper.get(e);
            ShapeComponent shape = shapeMapper.get(e);
            Polygon collPoly = new Polygon(new float[] { 0, 0, shape.width, 0, shape.width, shape.height, 0, shape.height });
            collPoly.setPosition(pos.x, pos.y);
            float[] vertices = collPoly.getTransformedVertices();
            Vector2 topLeft = new Vector2(vertices[0], vertices[1]);
            Vector2 topRight = new Vector2(vertices[2], vertices[3]);
            Vector2 bottomRight = new Vector2(vertices[4], vertices[5]);
            Vector2 bottomLeft = new Vector2(vertices[6], vertices[7]);
            boolean slicedRight = Intersector.intersectSegments(sliceStart, sliceEnd, topRight, bottomRight, null);
            boolean slicedLeft = Intersector.intersectSegments(sliceStart, sliceEnd, bottomLeft, topLeft, null);
            if (slicedLeft && slicedRight) {
                deadMapper.create(e);
                sliceableMapper.remove(e);
            }
        }
    }
}