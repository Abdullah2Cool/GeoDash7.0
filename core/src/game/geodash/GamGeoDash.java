package game.geodash;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import box2dLight.RayHandler;

public class GamGeoDash extends Game {
    ScrPlay scrPlay;
    ScrSplashScreen scrSplashScreen;
    ScrMenu scrMenu;
    OrthographicCamera camera;
    public static World world;
    Box2DDebugRenderer b2dr;
    SpriteBatch batch;
    RayHandler rayHandler;
    ShapeRenderer shape;
    Viewport viewport;
    float Game_Width = 500;
    float Game_Height = 375;
    public static final float PPM = 32;
    public static boolean bPlayerDead, bJump = true, bBoat = false, bFlipGrav = false;
    public static float fAmbiance = 0;
    public static String sPlayer = "player", sSpike = "spike", sPlatform = "platform", sBoatPortal = "boatportal",
            sGravPortal = "gravportal";

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
        camera.position.x = scrPlay.getPlayer().getPosition().x + 200;
        camera.position.y = scrPlay.getPlayer().getPosition().y + 50;
        camera.position.x = MathUtils.clamp(camera.position.x, 250, 14500);
        camera.position.y = MathUtils.clamp(camera.position.y, 100, 450);
//        System.out.println(scrPlay.getPlayer().getPosition().x);
//        System.out.println(camera.position.x);
//        System.out.println();

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        shape.setProjectionMatrix(camera.combined);
        b2dr.render(world, camera.combined);
//        rayHandler.setCombinedMatrix(camera.combined, camera.position.x, camera.position.y, camera.viewportWidth, camera.viewportHeight);
//        rayHandler.setAmbientLight(fAmbiance, fAmbiance, fAmbiance, 1);
        rayHandler.setAmbientLight(fAmbiance);
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
    public void loadObstacles(MapObjects obstacles, String sUserData, boolean isSensor) {
        for (MapObject object : obstacles) {
            Shape shape;
            shape = createPolyLine((PolylineMapObject) object);
            Body body;
            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.StaticBody;

            FixtureDef fixDef = new FixtureDef();
            fixDef.density = 1.0f;
            fixDef.isSensor = isSensor;
            fixDef.shape = shape;

            body = world.createBody(def);
            body.createFixture(fixDef).setUserData(sUserData);
            shape.dispose();
        }
    }

    public ChainShape createPolyLine(PolylineMapObject obstacle) {
        float[] vertices = obstacle.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < worldVertices.length; i++) {
            worldVertices[i] = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);
        }
        ChainShape chainShape = new ChainShape();
        chainShape.createChain(worldVertices);
        return chainShape;
    }
}
