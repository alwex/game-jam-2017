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
import com.alwex.ggj.utils.MyMaths;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
    SpriteBatch staticBatch;
    TiledMap map;
    World world;
    ShapeRenderer shapeRenderer;
    DeltaSystem deltaSystem;

    Vector3 mousePosition;
    Vector2 screenResolution;

    private ShaderProgram focusShader;

    FrameBuffer pixelatedFbo;
    FrameBuffer blurFbo;


    public LevelScreen(final JamGame game, String mapName) {
        this.game = game;

        mousePosition = new Vector3(0, 0, 0);
        screenResolution = new Vector2(0, 0);

        focusShader = new ShaderProgram(
                Gdx.files.internal("shaders/passthrough.vert.glsl"),
                Gdx.files.internal("shaders/focus.frag.glsl")
        );
        if (!focusShader.isCompiled()) {
            Gdx.app.log("SHADER FOCUS", focusShader.getLog());
        }

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 32, 24);

        pixelatedFbo = new FrameBuffer(Pixmap.Format.RGBA8888,
                (int) camera.viewportWidth * 8,
                (int) camera.viewportWidth * 8,
                false
        );
        pixelatedFbo.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        blurFbo = new FrameBuffer(Pixmap.Format.RGBA8888,
                (int) camera.viewportWidth * 8,
                (int) camera.viewportWidth * 8,
                false
        );
        blurFbo.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        staticCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        staticBatch = game.getStaticBatch();

        shapeRenderer = new ShapeRenderer();

        batch = game.getBatch();

        map = new TmxMapLoader().load("maps/" + mapName);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 8f);
        deltaSystem = new DeltaSystem();

        WorldConfiguration config = new WorldConfigurationBuilder()
                .with(
                        new EventSystem(),
                        deltaSystem,

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
                                map.getProperties().get("height", Integer.class),
                                game.getTweenManager()
                        ),
                        new DeadFishSystem(),
                        new BleedingSystem(),
                        new InputSystem(camera),
                        new CameraSystem(camera),
                        new SliceableSystem(game.getTweenManager()),
                        new WaterSplashSystem(),
//                        new RenderSystem(batch, camera, shapeRenderer),
                        new SpriteRenderSystem(batch, camera, game.getAssetManager().get("sprites/atlas.atlas", TextureAtlas.class)),
                        new SpawnSystem(),
//                        new RenderSystem(batch, camera, shapeRenderer),
                        new WaterRenderSystem(shapeRenderer, camera),
                        new MicrophoneSystem(game.getRecorder(), shapeRenderer, 1024),
                        new GarbageSystem(),
                        new BounceOffEdgeSystem(
                                map.getProperties().get("width", Integer.class),
                                map.getProperties().get("height", Integer.class)
                        ),
                        new SoundSystem(game.getAssetManager()),
                        new BloodStainSystem(shapeRenderer, staticCamera),
                        new ComboSystem(),
                        new BloodRenderSystem(shapeRenderer, camera),
                        new GuiSystem(this, batch, staticCamera, game.getTweenManager()),
                        new CameraShakingSystem(camera)
                ).build();

        world = new World(config);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // clear the screen with plain black

        pixelatedFbo.begin();
        {

            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Gdx.gl.glEnable(GL20.GL_BLEND);

            game.getTweenManager().update(delta);
            world.setDelta(delta );
            world.process();

            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
        pixelatedFbo.end();


        screenResolution.x = Gdx.graphics.getWidth();
        screenResolution.y = Gdx.graphics.getHeight();
        mousePosition.x = Gdx.input.getX();
        mousePosition.y = Gdx.graphics.getHeight() - Gdx.input.getY();

        focusShader.begin();
        {
            float deltaFactor = world.getSystem(DeltaSystem.class).getDeltaFactor();

            // setting the shader uniforms
            focusShader.setUniformf("u_resolution", screenResolution);
//            focusShader.setUniformf("u_time", delta);
            focusShader.setUniformf("u_position", mousePosition);
            focusShader.setUniformf("u_intensity", MyMaths.floatMap(deltaFactor, 1, 0, 0, 0.05f));

        }
        focusShader.end();

        staticBatch.setProjectionMatrix(staticCamera.combined);
        staticBatch.setShader(focusShader);
        staticBatch.begin();
        {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            staticBatch.draw(pixelatedFbo.getColorBufferTexture(), -staticCamera.viewportWidth / 2, -staticCamera.viewportHeight / 2, staticCamera.viewportWidth, staticCamera.viewportHeight, 0, 0, 1, 1);
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
        staticBatch.end();

        world.getSystem(GuiSystem.class).process();
    }

    @Override
    public void resize(int width, int height) {
//        staticCamera.setToOrtho(false, width, height);
//        staticCamera.update(true);
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
        pixelatedFbo.dispose();
    }
}
