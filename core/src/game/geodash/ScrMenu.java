package game.geodash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import box2dLight.RayHandler;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by hafiz on 1/10/2017.
 */
public class ScrMenu implements Screen {

    private GamGeoDash game;
    private SpriteBatch batch;
    private Sprite sprPlay;
    private RayHandler rayHandler;
    private boolean bPlay = false;
    private Map map;

    public ScrMenu(GamGeoDash game) {
        this.game = game;
        rayHandler = game.rayHandler;
        batch = game.batch;
//        map = new Map("map2.tmx", game.world, false);
        sprPlay = new Sprite(new Texture("play.png"));
        sprPlay.setX(180);
        sprPlay.setY(220);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(46 / 255f, 210 / 255f, 255 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.fAmbiance = 1;


//        System.out.println("X:" + Gdx.input.getX());
//        System.out.println("Y:" + Gdx.input.getY());

        batch.begin();
        batch.draw(sprPlay, sprPlay.getX(), sprPlay.getY(),
                sprPlay.getOriginX(), sprPlay.getOriginY(), sprPlay.getWidth(), sprPlay.getHeight(),
                sprPlay.getScaleX(), sprPlay.getScaleY(), 0);
        batch.end();

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                System.out.println("hit");
                bPlay = true;
            if (bPlay) {
                game.setScreen(game.scrPlay);
            }
        }
 //    map.draw(game.camera);


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