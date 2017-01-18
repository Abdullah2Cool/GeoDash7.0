package game.geodash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.RayHandler;

import static game.geodash.GamGeoDash.PPM;
import static game.geodash.GamGeoDash.bJump;

/**
 * Created by hafiz on 12/13/2016.
 */

public class Player implements InputProcessor {
    private Vector2 vPos, vInitialPos;
    private World world;
    private Body pBody;
    private Sprite spPlayer;
    private float fSpeed, fJumpHeight;
    private Light light;

    public Player(Vector2 vPos, float fLength, World world, String sPath, RayHandler rayHandler) {
        this.vPos = new Vector2(vPos);
        vInitialPos = new Vector2(vPos);
        this.world = world;
        pBody = createBody(vPos, fLength);
        spPlayer = new Sprite(new Texture(sPath));
//        fSpeed = 8.2f;
        fSpeed = 10f;
        fJumpHeight = 1600;
        System.out.println(fJumpHeight);
        Gdx.input.setInputProcessor(this);
        light = new Light(rayHandler, 100, 300, 50);
    }

    private Body createBody(Vector2 vPos, float fLength) {
        Body pBody;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        def.position.set(vPos.x / PPM, vPos.y / PPM);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(fLength / 2 / PPM, fLength / 2 / PPM);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = shape;
        fixDef.density = 1.25f;
//        fixDef.restitution = 0.5f;

        pBody = world.createBody(def);
        pBody.createFixture(fixDef).setUserData("player");
        shape.dispose();
        return pBody;
    }

    public void draw(SpriteBatch batch) {
        vPos.set(pBody.getPosition().scl(PPM));
        batch.draw(spPlayer, vPos.x - 16, vPos.y - 16, spPlayer.getOriginX(), spPlayer.getOriginY(),
                spPlayer.getWidth(), spPlayer.getHeight(), 1, 1, (float) Math.toDegrees(pBody.getAngle()));
        move();
        light.update(vPos);
    }

    public void move() {
        pBody.setLinearVelocity(fSpeed, pBody.getLinearVelocity().y);
    }

    public void reset() {
        pBody.setTransform(vInitialPos.x / PPM, vInitialPos.y / PPM, 0);
    }

    public Vector2 getPosition() {
        return vPos;
    }

    @Override
    public boolean keyDown(int keycode) {

        if (keycode == Input.Keys.SPACE && bJump) {
            pBody.applyForceToCenter(0, fJumpHeight, false);
            bJump = false;
        } else if (keycode == Input.Keys.LEFT || keycode == Input.Keys.RIGHT) {
            fSpeed *= -1;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (bJump) {
            pBody.applyForceToCenter(0, fJumpHeight, false);
            bJump = false;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
