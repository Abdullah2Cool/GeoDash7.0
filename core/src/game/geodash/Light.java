package game.geodash;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import box2dLight.ConeLight;
import box2dLight.RayHandler;

/**
 * Created by hafiz on 1/4/2017.
 */

public class Light {
    ConeLight light;

    public Light(RayHandler rayHandler, int nRays, int nDist, float coneDegrees, Body body) {
        light = new ConeLight(rayHandler, nRays, Color.GOLD, nDist, 0, 0, 0, coneDegrees);
//        light.attachToBody(body);
    }
    void update (Vector2 vPos) {
        light.setPosition(vPos.x, vPos.y);
    }
}
