package game.geodash;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import box2dLight.RayHandler;

public class GamGeoDash extends Game {
    ScrPlay scrPlay;
    ScrSplashScreen scrSplashScreen;
    ScrMenu scrMenu;
    OrthographicCamera camera;
    World world;
    Box2DDebugRenderer b2dr;
    SpriteBatch batch;
    RayHandler rayHandler;
    ShapeRenderer shape;
    Viewport viewport;
    float Game_Width = 500;
    float Game_Height = 375;
    public static final float PPM = 32;
    public static boolean bPlayerDead;
    public static float fAmbiance = 0;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(Game_Width, Game_Height, camera);
        world = new World(new Vector2(0, -100), true);
        b2dr = new Box2DDebugRenderer();
        rayHandler = new RayHandler(world);
        shape = new ShapeRenderer();
        scrPlay = new ScrPlay(this);
        scrSplashScreen = new ScrSplashScreen(this);
        scrMenu = new ScrMenu(this);
        bPlayerDead = false;
        setScreen(scrSplashScreen);
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }

    @Override
    public void render() {
        super.render();
        updateView();
    }

    @Override
    public Screen getScreen() {
        return super.getScreen();
    }

    public void updateView() {
        world.step(1 / 60f, 6, 2);
        camera.update();
        camera.position.x = scrPlay.getPlayer().getPosition().x + 50;
//        camera.position.y = scrPlay.getPlayer().getPosition().y + 50;
//        System.out.println(scrPlay.getPlayer().getPosition());
        System.out.println(camera.position);
        camera.position.x = MathUtils.clamp(camera.position.x, 250, 14500);
        camera.position.y = MathUtils.clamp(camera.position.y, 100, 400);

        batch.setProjectionMatrix(camera.combined);
        shape.setProjectionMatrix(camera.combined);
        b2dr.render(world, camera.combined);
//        rayHandler.setCombinedMatrix(camera.combined, camera.position.x, camera.position.y, camera.viewportWidth, camera.viewportHeight);
        rayHandler.setCombinedMatrix(camera.combined, camera.position.x , camera.position.y,
                camera.viewportWidth, camera.viewportHeight);
        rayHandler.updateAndRender();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
        viewport.apply(true);
        camera.position.set(camera.position.x, camera.position.y + 100, 0);
    }

    @Override
    public void dispose() {
        super.dispose();
        world.dispose();
        b2dr.dispose();
        batch.dispose();
        shape.dispose();
        rayHandler.dispose();
    }
}
