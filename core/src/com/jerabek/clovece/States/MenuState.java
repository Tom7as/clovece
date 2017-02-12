package com.jerabek.clovece.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.jerabek.clovece.CloveceNezlobSe;
import com.jerabek.clovece.Field;
import javax.swing.Renderer;

import static com.jerabek.clovece.CloveceNezlobSe.appHeight;
import static com.jerabek.clovece.CloveceNezlobSe.appWidth;

/**
 * Created by Tomas on 2/11/2017.
 */
public class MenuState extends State{
    private Texture pole;
    private Texture playBtn;
    Stage stage;


    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, appWidth / 2, CloveceNezlobSe.appHeight / 2);
        pole = new Texture("pole.png");
        playBtn = new Texture("playbtn.png");

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }


    @Override
    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        Gdx.gl.glClearColor(0.8f, 0.8f, 0.6f, 1);

        for(int i = 0; i < field.length; i++) {
            field[i].getSequence();
            field[i].getColor();
            //pole(field[i].getX(), field[i].getY());
            sb.draw(pole, field[i].getX()*(appWidth/30)+(8*(appWidth/30)), field[i].getY()*(appWidth/30)+(4*(appWidth/30)));
        }
        sb.end();
        stage = new Stage(new ExtendViewport(960, 1280, 960, 1600, cam));
        //stage = new Stage(new StretchViewport(1080,1800, cam));

    }

    @Override
    public void dispose() {
        pole.dispose();
        playBtn.dispose();
        System.out.println("Menu State Disposed");
    }
    public Field[] field = new Field[]{
            new Field(0, -5, -1, 0, 0, 0),
            new Field(1, -5, 0, 0, 0, 0),
            new Field(2, -5, 1, 0, 0, 0),
            new Field(3, -4, 1, 0, 0, 0),
            new Field(4, -3, 1, 0, 0, 0),
            new Field(5, -2, 1, 0, 0, 0),
            new Field(6, -1, 1, 0, 0, 0),
            new Field(7, -1, 2, 0, 0, 0),
            new Field(8, -1, 3, 0, 0, 0),
            new Field(9, -1, 4, 0, 0, 0),
            new Field(10, -1, 5, 0, 0, 0),
            new Field(11, 0, 5, 0, 0, 0),
            new Field(12, 1, 5, 0, 0, 0),
            new Field(13, 1, 4, 0, 0, 0),
            new Field(14, 1, 3, 0, 0, 0),
            new Field(15, 1, 2, 0, 0, 0),
            new Field(16, 1, 1, 0, 0, 0),
            new Field(17, 2, 1, 0, 0, 0),
            new Field(18, 3, 1, 0, 0, 0),
            new Field(19, 4, 1, 0, 0, 0),
            new Field(20, 5, 1, 0, 0, 0),
            new Field(21, 5, 0, 0, 0, 0),
            new Field(22, 5, -1, 0, 0, 0),
            new Field(23, 4, -1, 0, 0, 0),
            new Field(24, 3, -1, 0, 0, 0),
            new Field(25, 2, -1, 0, 0, 0),
            new Field(26, 1, -1, 0, 0, 0),
            new Field(27, 1, -2, 0, 0, 0),
            new Field(28, 1, -3, 0, 0, 0),
            new Field(29, 1, -4, 0, 0, 0),
            new Field(30, 1, -5, 0, 0, 0),
            new Field(31, 0, -5, 0, 0, 0),
            new Field(32, -1, -5, 0, 0, 0),
            new Field(33, -1, -4, 0, 0, 0),
            new Field(34, -1, -3, 0, 0, 0),
            new Field(35, -1, -2, 0, 0, 0),
            new Field(36, -1, -1, 0, 0, 0),
            new Field(37, -2, -1, 0, 0, 0),
            new Field(38, -3, -1, 0, 0, 0),
            new Field(39, -4, -1, 0, 0, 0),
    };
}