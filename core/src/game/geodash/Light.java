package game.geodash;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import box2dLight.ConeLight;
import box2dLight.RayHandler;

/**
 * Created by hafiz on 1/4/2017.
 */

public class Light {
    ConeLight light;

    public Light(RayHandler rayHandler, int nRays, int nDist, float coneDegrees) {
        light = new ConeLight(rayHandler, nRays, Color.GOLD, nDist, 0, 0, 0, coneDegrees);
    }
    void update (Vector2 vPos) {
        light.setPosition(vPos.x, vPos.y);
    }
}
