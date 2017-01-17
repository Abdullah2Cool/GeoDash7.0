package game.geodash;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import static game.geodash.GamGeoDash.bJump;
import static game.geodash.GamGeoDash.bPlayerDead;

/**
 * Created by hafiz on 12/18/2016.
 */

public class ContactListener1 implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null) {
            return;
        }
        if (a.getUserData() == null || b.getUserData() == null) {
            return;
        }
        if (CheckContact(a, b, "player", "spike")) {
            bPlayerDead = true;
        }

        if (CheckContact(a, b, "player", "platform")) {
            bJump = true;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean CheckContact(Fixture a, Fixture b, String sDef1, String sDef2) {
        if (a.getUserData() == sDef1 || b.getUserData() == sDef1) {
            if (a.getUserData() == sDef2 || b.getUserData() == sDef2) {
                return true;
            }
        }
        return false;
    }
}
