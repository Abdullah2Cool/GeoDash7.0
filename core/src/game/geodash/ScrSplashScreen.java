package game.geodash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import box2dLight.RayHandler;

import static game.geodash.GamGeoDash.fAmbiance;

/**
 * Created by hafiz on 1/9/2017.
 */
public class ScrSplashScreen implements Screen {

    private GamGeoDash game;
    private SpriteBatch batch;
    private RayHandler rayHandler;
    private float fScale;
    private float fLoading;
    private Sprite spLogo;
    private ShapeRenderer shape;
    private boolean bLights = true, bShowLogo = false, bShowLoading = false, bDimOut = false;

    public ScrSplashScreen(GamGeoDash game) {
        this.game = game;
        this.batch = game.batch;
        this.rayHandler = game.rayHandler;
        spLogo = new Sprite(new Texture("logo.png"));
        shape = game.shape;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(46 / 255f, 210 / 255f, 255 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (bLights) {
            fAmbiance += 0.01;
            if (fAmbiance >= 1) {
                bLights = false;
                bShowLogo = true;
            }
        }
        if (bShowLogo) {
            if (fScale <= 0.8f) {
                fScale += 0.05;
                spLogo.setScale(fScale);
            } else {
                bShowLoading = true;
            }
            batch.begin();
            batch.draw(spLogo, -50, 200,
                    spLogo.getOriginX(), spLogo.getOriginY(), spLogo.getWidth(), spLogo.getHeight(),
                    spLogo.getScaleX(), spLogo.getScaleY(), 0);
            batch.end();
        }
        if (bShowLoading) {
            if (fLoading <= 200) {
//                fLoading += 5;
                fLoading += Math.random() * 5 + 1;
            } else {
                bDimOut = true;
            }
            shape.begin(ShapeRenderer.ShapeType.Line);
            shape.setColor(Color.BLACK);
            shape.rect(0, 0, 200, 40);
            shape.end();

            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(Color.RED);
            shape.rect(150, 200, fLoading, 40);
            shape.end();
        }
        if (bDimOut) {
            fAmbiance -= 0.1;
            if (fAmbiance <= 0) {
                fAmbiance = 0;
                game.setScreen(game.scrMenu);
            }
        }
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