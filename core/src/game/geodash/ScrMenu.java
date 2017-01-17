package game.geodash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import box2dLight.RayHandler;

import static game.geodash.GamGeoDash.fAmbiance;

/**
 * Created by hafiz on 1/10/2017.
 */

public class ScrMenu implements Screen {
    private GamGeoDash game;
    private SpriteBatch batch;
    private RayHandler rayHandler;
    private boolean bLightUp = true;
    private Map map;

    public ScrMenu (GamGeoDash game) {
        this.game = game;
        rayHandler = game.rayHandler;
        batch = game.batch;
//        map = new Map("map2.tmx", game.world, false, game);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(46 / 255f, 210 / 255f, 255 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (bLightUp) {
            fAmbiance += 0.1;
            if (fAmbiance >= 1) {
                bLightUp = false;
            }
        }
        if (bLightUp == false) {
            game.setScreen(game.scrPlay);
        }
//        map.draw(game.camera);
        rayHandler.setAmbientLight(fAmbiance);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}