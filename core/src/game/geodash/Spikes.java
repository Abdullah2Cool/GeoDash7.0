package game.geodash;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import static game.geodash.GamGeoDash.PPM;


/**
 * Created by hafiz on 12/18/2016.
 */
// This is only necessary because we had to give each spike it's own identity (userData) so that we can detect specific collisions
// between the player and the spike (instead of any box2d body).
public class Spikes {
    World world;

    Spikes(TiledMap map, World world, String sObjectLayer) {
        this.world = world;
        loadSpikes(map.getLayers().get(sObjectLayer).getObjects());
    }

    private void loadSpikes(MapObjects spike) {
        for (MapObject object : spike) {
            Shape shape;
            shape = createPolyLine((PolylineMapObject) object);
            Body body;
            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.StaticBody;

            FixtureDef fixDef = new FixtureDef();
            fixDef.density = 1.0f;
            fixDef.shape = shape;

            body = world.createBody(def);
            body.createFixture(fixDef).setUserData(this);
            shape.dispose();
        }
    }

    private ChainShape createPolyLine(PolylineMapObject obstacle) {
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
