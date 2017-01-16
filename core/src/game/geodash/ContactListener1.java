package game.geodash;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

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
        if (PlayerSpikeContact(a, b) == true) {
            bPlayerDead = true;
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

    public boolean PlayerSpikeContact(Fixture a, Fixture b) {
        if (a.getUserData() instanceof Player || b.getUserData() instanceof Player) {
            if (a.getUserData() instanceof Spikes || b.getUserData() instanceof Spikes) {
                return true;
            }
        }
        return false;
    }
}
