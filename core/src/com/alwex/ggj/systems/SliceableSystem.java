package com.alwex.ggj.systems;

import aurelienribon.tweenengine.TweenManager;
import com.alwex.ggj.components.*;
import com.alwex.ggj.events.CameraShakeEvent;
import com.alwex.ggj.events.SlicedEvent;
import com.alwex.ggj.factory.EntityFactory;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
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

    TweenManager tweenManager;

    Vector2 topLeft;
    Vector2 topRight;
    Vector2 bottomRight;
    Vector2 bottomLeft;

    public boolean isSlicing;
    public Vector2 sliceStart;
    public Vector2 sliceEnd;
    public long sliceStartTime;

    public SliceableSystem(TweenManager tweenManager) {
        super(Aspect.all(SliceableComponent.class));
        this.tweenManager = tweenManager;
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
            Polygon collPoly = new Polygon(new float[]{0, 0, shape.width, 0, shape.width, shape.height, 0, shape.height});
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
            boolean slicedTop = Intersector.intersectSegments(sliceStart, sliceEnd, topLeft, topRight, null);
            boolean slicedRight = Intersector.intersectSegments(sliceStart, sliceEnd, topRight, bottomRight, null);
            boolean slicedBottom = Intersector.intersectSegments(sliceStart, sliceEnd, bottomRight, bottomLeft, null);
            boolean slicedLeft = Intersector.intersectSegments(sliceStart, sliceEnd, bottomLeft, topLeft, null);
            if ((slicedLeft && slicedTop) ||
                    (slicedLeft && slicedRight) ||
                    (slicedLeft && slicedBottom) ||
                    (slicedTop && slicedRight) ||
                    (slicedTop && slicedBottom) ||
                    (slicedRight && slicedBottom)) {
                deadMapper.create(e);
                sliceableMapper.remove(e);
                eventSystem.dispatch(new SlicedEvent(e.getId()));

                EntityFactory.instance.createSlicedFish(world, e, tweenManager);
                e.deleteFromWorld();


                for (int i = 0; i <= MathUtils.random(3, 5); i++) {
                    float red = MathUtils.random(0.5f, 1f);
                    float alpha = MathUtils.random(0.5f, 1f);


                    float x = MathUtils.random(-Gdx.graphics.getWidth() / 2, Gdx.graphics.getWidth() / 2);
                    float y = MathUtils.random(-Gdx.graphics.getHeight() / 2, Gdx.graphics.getHeight() / 2);
                    if (Math.abs(x) > 180 || Math.abs(y) > 180) {
                        world.createEntity().edit()
                                .add(new BloodStainComponent(
                                        x,
                                        y,
                                        MathUtils.random(10f, 50f),
                                        new Color(red, 0, 0, alpha)
                                ));
                    }
                }
                //eventSystem.dispatch(new CameraShakeEvent(0.25f, 100f));
            }
        }
    }
}
