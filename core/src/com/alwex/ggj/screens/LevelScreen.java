package com.alwex.ggj.screens;

import com.alwex.ggj.JamGame;
import com.alwex.ggj.components.MicrophoneComponent;
import com.alwex.ggj.components.PositionComponent;
import com.alwex.ggj.components.ShapeComponent;
import com.alwex.ggj.systems.MicrophoneRenderSystem;
import com.alwex.ggj.systems.PositionSystem;
import com.alwex.ggj.systems.MicrophoneSystem;
import com.alwex.ggj.systems.RenderSystem;
import com.alwex.ggj.systems.*;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.system.render.MapRenderSystem;

/**
 * Created by alexandreguidet on 20/01/17.
 */
public class LevelScreen implements Screen {

    final JamGame game;

    OrthogonalTiledMapRenderer mapRenderer;
    OrthographicCamera camera;
    OrthographicCamera staticCamera;
    SpriteBatch batch;
    TiledMap map;
    World world;
    ShapeRenderer shapeRenderer;

    public LevelScreen(final JamGame game, String mapName) {
        this.game = game;

        camera = new OrthographicCamera(32, 24);
        camera.setToOrtho(false, 32, 24);

        shapeRenderer = new ShapeRenderer();

        batch = game.getBatch();

        map = new TmxMapLoader().load("maps/" + mapName);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 8f);

        WorldConfiguration config = new WorldConfigurationBuilder()
                .with(
                        new EventSystem(),

                        // other systems goes here
                        new PositionSystem(),
                        new ShapeTweeningSystem(game.getTweenManager()),
                        new RotationSystem(),
                        new SkyRenderSystem(shapeRenderer, camera,
                                map.getProperties().get("width", Integer.class),
                                map.getProperties().get("height", Integer.class)
                        ),
                        new MapSystem(mapRenderer, camera),
                        new PhysicSystem(
                                -25f,
                                map.getProperties().get("width", Integer.class),
                                map.getProperties().get("height", Integer.class)
                        ),
                        new WaterSystem(
                                map.getProperties().get("width", Integer.class),
                                map.getProperties().get("height", Integer.class)
                        ),
                        new FishSystem(
                                map.getProperties().get("width", Integer.class),
                                map.getProperties().get("height", Integer.class),
                                camera
                        ),
                        new CloudSystem(
                                map.getProperties().get("width", Integer.class),
                                map.getProperties().get("height", Integer.class)
                        ),
                        new DeadFishSystem(),
                        new BleedingSystem(),
                        new BloodRenderSystem(shapeRenderer, camera),
                        new InputSystem(camera),
                        new CameraSystem(camera),
                        new SliceableSystem(),
//                        new RenderSystem(batch, camera, shapeRenderer),
                        new SpriteRenderSystem(batch, camera, game.getAssetManager().get("sprites/atlas.atlas", TextureAtlas.class)),
                        new SpawnSystem(),
//                        new RenderSystem(batch, camera, shapeRenderer),
                        new WaterRenderSystem(shapeRenderer, camera),
                        new MicrophoneSystem(game.getRecorder(), shapeRenderer, 1024),
                        new GarbageSystem(),
                        new SoundSystem(game.getAssetManager()),
                        new BounceOffEdgeSystem(
                                map.getProperties().get("width", Integer.class),
                                map.getProperties().get("height", Integer.class)
                        )
                ).build();

        world = new World(config);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // clear the screen with plain black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getTweenManager().update(delta);
        world.setDelta(delta);
        world.process();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        resume();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();
    }
}
