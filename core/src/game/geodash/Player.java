package game.geodash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

import box2dLight.RayHandler;

import static game.geodash.GamGeoDash.PPM;
import static game.geodash.GamGeoDash.bJump;
import static game.geodash.GamGeoDash.sPlayer;

/**
 * Created by hafiz on 12/13/2016.
 */

public class Player {
    private Vector2 vPos, vInitialPos;
    private World world;
    private Body pBody;
    private Sprite spPlayer;
    private float fSpeed, fJumpHeight;
    private Light light;
    private TextureAtlas atlas;
    private int nImgNum = 1;
    private Random random;

    public Player(Vector2 vPos, float fLength, World world, String sPath, RayHandler rayHandler) {
        this.vPos = new Vector2(vPos);
        vInitialPos = new Vector2(vPos);
        this.world = world;
        pBody = createBody(vPos, fLength);
        atlas = new TextureAtlas("packedtextures.atlas");
        spPlayer = new Sprite(atlas.findRegion(String.valueOf(nImgNum)));
        fSpeed = 10f;
        fJumpHeight = 1600;
        System.out.println(fJumpHeight);
        light = new Light(rayHandler, 100, 300, 50);
        random = new Random();
    }

    private Body createBody(Vector2 vPos, float fLength) {
        Body pBody;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = false;
        def.position.set(vPos.x / PPM, vPos.y / PPM);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(fLength / 2 / PPM, fLength / 2 / PPM);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = shape;
        fixDef.density = 1.25f;
//        fixDef.restitution = 0.5f;

        pBody = world.createBody(def);
        pBody.createFixture(fixDef).setUserData(sPlayer);
        shape.dispose();
        return pBody;
    }

    public void draw(SpriteBatch batch) {
        vPos.set(pBody.getPosition().scl(PPM));
        batch.draw(spPlayer, vPos.x - 16, vPos.y - 16, spPlayer.getOriginX(), spPlayer.getOriginY(),
                spPlayer.getWidth(), spPlayer.getHeight(), 1, 1, (float) Math.toDegrees(pBody.getAngle()));

        light.update(vPos);
        move();
    }

    public void move() {
        pBody.setLinearVelocity(fSpeed, pBody.getLinearVelocity().y);
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && bJump) {
            pBody.applyForceToCenter(0, fJumpHeight, false);
            bJump = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            fSpeed *= -1;
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && bJump) {
            pBody.applyForceToCenter(0, fJumpHeight, false);
            bJump = false;
        }
    }

    public void reset() {
        pBody.setTransform(vInitialPos.x / PPM, vInitialPos.y / PPM, 0);
    }

    public Vector2 getPosition() {
        return vPos;
    }

    public void changeImage(int max, int min) {
        nImgNum = random.nextInt(max) + min;
        spPlayer.setRegion(atlas.findRegion(String.valueOf(nImgNum)));
    }
}
