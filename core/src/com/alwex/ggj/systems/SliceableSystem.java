package com.alwex.ggj.systems;

import com.alwex.ggj.components.DeadComponent;
import com.alwex.ggj.components.PositionComponent;
import com.alwex.ggj.components.ShapeComponent;
import com.alwex.ggj.components.SliceableComponent;
import com.alwex.ggj.events.SlicedEvent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import net.mostlyoriginal.api.event.common.EventSystem;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by jbrungar on 20/01/17.
 */
@Wire
public class SliceableSystem extends EntityProcessingSystem {

    EventSystem eventSystem;
    ComponentMapper<PositionComponent> positionMapper;
    ComponentMapper<ShapeComponent> shapeMapper;
    ComponentMapper<DeadComponent> deadMapper;
    ComponentMapper<SliceableComponent> sliceableMapper;

    Vector2 topLeft;
    Vector2 topRight;
    Vector2 bottomRight;
    Vector2 bottomLeft;

    public boolean isSlicing;
    public Vector2 sliceStart;
    public Vector2 sliceEnd;
    public long sliceStartTime;

    public SliceableSystem() {
        super(Aspect.all(SliceableComponent.class));
    }

    @Override
    protected void initialize() {
        isSlicing = false;
        sliceStart = new Vector2();
        sliceEnd = new Vector2();
        sliceStartTime = 0;

        topLeft = new Vector2();
        topRight = new Vector2();
        bottomRight = new Vector2();
        bottomLeft = new Vector2();
    }

    @Override
    protected void process(Entity e) {
        if (isSlicing && TimeUtils.timeSinceMillis(sliceStartTime) < 200) {
            PositionComponent pos = positionMapper.get(e);
            ShapeComponent shape = shapeMapper.get(e);
            Polygon collPoly = new Polygon(new float[] { 0, 0, shape.width, 0, shape.width, shape.height, 0, shape.height });
            collPoly.setPosition(pos.x, pos.y);
            float[] vertices = collPoly.getTransformedVertices();
            topLeft.x = vertices[0];
            topLeft.y = vertices[1];
            topRight.x = vertices[2];
            topRight.y = vertices[3];
            bottomRight.x = vertices[4];
            bottomRight.y = vertices[5];
            bottomLeft.x = vertices[6];
            bottomLeft.y = vertices[7];
            boolean slicedRight = Intersector.intersectSegments(sliceStart, sliceEnd, topRight, bottomRight, null);
            boolean slicedLeft = Intersector.intersectSegments(sliceStart, sliceEnd, bottomLeft, topLeft, null);
            if (slicedLeft && slicedRight) {
                deadMapper.create(e);
                sliceableMapper.remove(e);

                eventSystem.dispatch(new SlicedEvent(e.getId()));
            }
        }
    }
}
