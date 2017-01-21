package com.alwex.ggj.factory;

import com.alwex.ggj.components.*;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import net.mostlyoriginal.api.component.basic.Pos;

/**
 * Created by samsung on 21/01/2017.
 */
public class EntityFactory {
    public static EntityFactory instance = new EntityFactory();

    ComponentMapper<PositionComponent> positionMapper;
    ComponentMapper<SpriteComponent> spiteMapper;

    private EntityFactory() {

    }


    public Entity createFish(
            World world,
            String name,
            FishDescriptor descriptor,
            float x, float y, float vx, float vy
    ) {

        float scale = MathUtils.random(1, 1);

        Entity fish = world.createEntity()
                .edit()
                .add(new FishComponent())
                .add(new PositionComponent(x, y))
                .add(new ShapeComponent(descriptor.width, descriptor.height))
//                .add(new SliceableComponent())
                .add(new SpriteComponent(name))
                .add(new RotationComponent(0, MathUtils.random(0.5f, 1f)))
                .add(new PhysicComponent(
                        MathUtils.random(0.5f, 1f),
                        vx, vy
                ))
                .getEntity();

        return fish;
    }

    public void createSlicedFish(World world, Entity fish) {
        ComponentMapper<PositionComponent> positionMapper = world.getMapper(PositionComponent.class);
        ComponentMapper<SpriteComponent> spriteMapper = world.getMapper(SpriteComponent.class);
        ComponentMapper<ShapeComponent> shapeMapper = world.getMapper(ShapeComponent.class);

        PositionComponent position = positionMapper.get(fish);
        SpriteComponent sprite = spriteMapper.get(fish);
        ShapeComponent shape = shapeMapper.get(fish);

        Entity leftPart = this.createFish(
                world, sprite.name + "-a",
                new FishDescriptor(sprite.name + "-a", shape.width / 2, shape.height),
                position.x, position.y,
                -2, 0
        );
        leftPart.edit()
                .add(new BleedingComponent())
                .remove(SliceableComponent.class);

        Entity rightPart = this.createFish(
                world, sprite.name + "-b",
                new FishDescriptor(sprite.name + "-b", shape.width / 2, shape.height),
                position.x, position.y,
                2, 0
        );
        rightPart.edit()
                .add(new BleedingComponent())
                .remove(SliceableComponent.class);

    }

    public Entity createBloodDrop(World world, float x, float y, float vx, float vy) {

        float size = MathUtils.random(0.1f, 0.25f);
        float red = MathUtils.random(0.5f, 1f);
        Color bloodColor = new Color(red, 0, 0, 0.4f);

        return world.createEntity().edit()
                .add(new PositionComponent(x, y))
                .add(new PhysicComponent(MathUtils.random(0.5f, 1f), vx, vy))
                .add(new BloodDropComponent(
                        size,
                        size,
                        bloodColor
                ))
                .getEntity();
    }

    public Entity createCloud(World world, CloudDescriptor descriptor, float x, float y, float vx, float vy) {
        return world.createEntity()
                .edit()
                .add(new RotationComponent(0, 0))
                .add(new CloudComponent())
                .add(new PositionComponent(x, y))
                .add(new SpriteComponent(descriptor.name))
                .add(new ShapeComponent(descriptor.width, descriptor.height))
                .add(new PhysicComponent(0, vx, vy))
                .getEntity();
    }
}
