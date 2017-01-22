package com.alwex.ggj.systems;

import com.alwex.ggj.components.EnemyComponent;
import com.alwex.ggj.components.SliceableComponent;
import com.alwex.ggj.events.EnemySlicedEvent;
import com.alwex.ggj.events.NoFishEvent;
import com.alwex.ggj.events.SlicedEvent;
import com.alwex.ggj.events.ThrowFishEvent;
import com.alwex.ggj.factory.EnemyDescriptor;
import com.alwex.ggj.factory.EntityFactory;
import com.artemis.*;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.artemis.systems.IntervalSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

import java.util.ArrayList;

/**
 * Created by Isaac on 22/01/2017.
 */
@Wire
public class EnemySystem extends IntervalSystem {

    ComponentMapper<EnemyComponent> enemyMapper;
    ArrayList<EnemyDescriptor> enemyDescriptors;

    EventSystem eventSystem;
    OrthographicCamera camera;
    int maxWidth, maxHeight;
    boolean canSpawnEnemies;

    public EnemySystem(OrthographicCamera camera, int maxWidth, int maxHeight) {
        super(Aspect.all(EnemyComponent.class, SliceableComponent.class),1f);
        this.camera = camera;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }


    @Override
    protected void initialize() {
        enemyDescriptors = new ArrayList<EnemyDescriptor>();
        enemyDescriptors.add(new EnemyDescriptor("boot", 2, 2, 0));
        enemyDescriptors.add(new EnemyDescriptor("bottle", 2, 2, 0));
        enemyDescriptors.add(new EnemyDescriptor("bomb", 4, 4, 0));


    }

    @Subscribe
    public void throwFishEvent(ThrowFishEvent event){
        canSpawnEnemies = true;
    }

    @Subscribe
    public void throwFishEvent(NoFishEvent event){
        canSpawnEnemies = false;
    }

    @Override
    protected void processSystem() {
        if(canSpawnEnemies) {
            EnemyDescriptor currentDescriptor = enemyDescriptors.get(MathUtils.random(0, enemyDescriptors.size() - 1));
            EntityFactory.instance.createEnemy(
                    world,
                    currentDescriptor.name,
                    currentDescriptor,
                    MathUtils.random(0, 32),
                    camera.position.y,
                    MathUtils.random(-30f, 30f),
                    MathUtils.random(0f, 30f)
            );
        }
    }

    @Subscribe
    public void onSliced(SlicedEvent event) {
        EnemyComponent enemy = enemyMapper.get(event.entityId);
        if(enemy != null) {
            eventSystem.dispatch(new EnemySlicedEvent());
        }
    }
}
