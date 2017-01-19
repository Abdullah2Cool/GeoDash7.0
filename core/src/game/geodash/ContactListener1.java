package game.geodash;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import static game.geodash.GamGeoDash.bBoat;
import static game.geodash.GamGeoDash.bJump;
import static game.geodash.GamGeoDash.bPlayerDead;
import static game.geodash.GamGeoDash.sBoatPortal;
import static game.geodash.GamGeoDash.sPlatform;
import static game.geodash.GamGeoDash.sPlayer;
import static game.geodash.GamGeoDash.sSpike;
import static game.geodash.ScrPlay.player;

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
        if (CheckContact(a, b, sPlayer, sSpike)) {
            player.changeImage(2, 1);
            bPlayerDead = true;
        }

        if (CheckContact(a, b, sPlayer, sPlatform) && bJump == false) {
            bJump = true;
        }

        if (CheckContact(a, b, sPlayer, sBoatPortal)) {
            if (bBoat == false) {
                player.changeImage(7, 3);
                bBoat = true;
            } else if (bBoat == true) {
                player.changeImage(2, 1);
                bBoat = false;
            }
            System.out.println("Boat Time");
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
