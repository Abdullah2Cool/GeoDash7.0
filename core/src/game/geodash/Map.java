package game.geodash;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by hafiz on 12/13/2016.
 */

public class Map {
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private TiledMap map;
    private World world;
//    Spikes spikes;

    public Map(String sPath, World world, boolean bObstacles, boolean bPortals, GamGeoDash game) {
        map = new TmxMapLoader().load(sPath);
        this.world = world;
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);
        game.loadObstacles(map.getLayers().get("platforms").getObjects(), "platform");
        if (bObstacles) {
//            spikes = new Spikes(map, world, "spike");
            game.loadObstacles(map.getLayers().get("death").getObjects(), "spike");
        }
        if (bPortals) {
            game.loadObstacles(map.getLayers().get("portals").getObjects(), "portal");
        }
    }

    public void draw(OrthographicCamera camera) {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

//    private void loadObstacles(MapObjects obstacles) {
//        for (MapObject obstacle : obstacles) {
//            Shape shape;
////            if (obstacle instanceof PolylineMapObject) {
//            shape = createPolyLine((PolylineMapObject) obstacle);
////            } else {
////                continue;
////            }
//            Body body;
//            BodyDef def = new BodyDef();
//            def.type = BodyDef.BodyType.StaticBody;
//            body = world.createBody(def);
//            body.createFixture(shape, 1.0f);
//            shape.dispose();
//        }
//    }
//
//    private ChainShape createPolyLine(PolylineMapObject obstacle) {
//        float[] vertices = obstacle.getPolyline().getTransformedVertices();
//        Vector2[] worldVertices = new Vector2[vertices.length / 2];
//
//        for (int i = 0; i < worldVertices.length; i++) {
//            worldVertices[i] = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);
//        }
//        ChainShape chainShape = new ChainShape();
//        chainShape.createChain(worldVertices);
//        return chainShape;
//    }

    public TiledMap getMap() {
        return map;
    }

    public OrthogonalTiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

}