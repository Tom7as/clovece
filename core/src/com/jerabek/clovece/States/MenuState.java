package com.jerabek.clovece.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jerabek.clovece.*;

import static com.jerabek.clovece.CloveceNezlobSe.appWidth;

/**
 * Created by Tomas on 2/11/2017.
 */
public class MenuState extends State {
    private Texture[] fieldImg = new Texture[5];

    Stage stage;
    float zoom = 70, fieldSize = 80, smallFieldSize = fieldSize*0.8f;


    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, appWidth / 2, CloveceNezlobSe.appHeight / 2);
        fieldImg[0] = new Texture("pole.png");
        fieldImg[1] = new Texture("poleR.png");
        fieldImg[2] = new Texture("poleY.png");
        fieldImg[3] = new Texture("poleG.png");
        fieldImg[4] = new Texture("poleB.png");

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            //gsm.set(new PlayState(gsm));
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
        Gdx.gl.glClearColor(0.2f, 0.8f, 0.9f, 1);

        refreshBoard(sb);

        sb.end();
        stage = new Stage(new ExtendViewport(960, 1280, 960, 1600, cam));
        //stage = new Stage(new StretchViewport(1080,1800, cam));

    }


    public void refreshBoard(SpriteBatch sb) {

        for(int i = 0; i < field.length; i++) {
            field[i].getSequence();
            field[i].getColor();
            //fieldImg(field[i].getX(), field[i].getY());
            if (field[i].getSequence() < 40) {
                sb.draw(fieldImg[field[i].getColor()], cam.position.x + field[i].getX() * zoom - fieldSize * 0.5f, cam.position.y + field[i].getY() * 70 - fieldSize * 0.5f);
            } else {
                //fieldImg(field[i].getX(), field[i].getY());
                sb.draw(fieldImg[field[i].getColor()], cam.position.x + field[i].getX() * zoom - smallFieldSize * 0.5f, cam.position.y + field[i].getY() * 70 - smallFieldSize * 0.5f, smallFieldSize, smallFieldSize);
            }
        }
    }

    @Override
    public void dispose() {
        for(int i = 0; i < 5; i++){
            fieldImg[i].dispose();
        }

        System.out.println("Menu State Disposed");
    }
    private GameField[] field = new GameField[]{
            new GameField(0, -5, -1, 0, 0, 4),
            new GameField(1, -5, 0, 0, 0, 0),
            new GameField(2, -5, 1, 0, 0, 0),
            new GameField(3, -4, 1, 0, 0, 0),
            new GameField(4, -3, 1, 0, 0, 0),
            new GameField(5, -2, 1, 0, 0, 0),
            new GameField(6, -1, 1, 0, 0, 0),
            new GameField(7, -1, 2, 0, 0, 0),
            new GameField(8, -1, 3, 0, 0, 0),
            new GameField(9, -1, 4, 0, 0, 0),
            new GameField(10, -1, 5, 0, 0, 2),
            new GameField(11, 0, 5, 0, 0, 0),
            new GameField(12, 1, 5, 0, 0, 0),
            new GameField(13, 1, 4, 0, 0, 0),
            new GameField(14, 1, 3, 0, 0, 0),
            new GameField(15, 1, 2, 0, 0, 0),
            new GameField(16, 1, 1, 0, 0, 0),
            new GameField(17, 2, 1, 0, 0, 0),
            new GameField(18, 3, 1, 0, 0, 0),
            new GameField(19, 4, 1, 0, 0, 0),
            new GameField(20, 5, 1, 0, 0, 1),
            new GameField(21, 5, 0, 0, 0, 0),
            new GameField(22, 5, -1, 0, 0, 0),
            new GameField(23, 4, -1, 0, 0, 0),
            new GameField(24, 3, -1, 0, 0, 0),
            new GameField(25, 2, -1, 0, 0, 0),
            new GameField(26, 1, -1, 0, 0, 0),
            new GameField(27, 1, -2, 0, 0, 0),
            new GameField(28, 1, -3, 0, 0, 0),
            new GameField(29, 1, -4, 0, 0, 0),
            new GameField(30, 1, -5, 0, 0, 3),
            new GameField(31, 0, -5, 0, 0, 0),
            new GameField(32, -1, -5, 0, 0, 0),
            new GameField(33, -1, -4, 0, 0, 0),
            new GameField(34, -1, -3, 0, 0, 0),
            new GameField(35, -1, -2, 0, 0, 0),
            new GameField(36, -1, -1, 0, 0, 0),
            new GameField(37, -2, -1, 0, 0, 0),
            new GameField(38, -3, -1, 0, 0, 0),
            new GameField(39, -4, -1, 0, 0, 0),
            //hrač1 start, cil red1
            new GameField(40, 5, 5, 1, 1, 1),
            new GameField(41, 4, 5, 1, 2, 1),
            new GameField(42, 5, 4, 1, 3, 1),
            new GameField(43, 4, 4, 1, 4, 1),
            new GameField(44, 4, 0, 0, 0, 1),
            new GameField(45, 3, 0, 0, 0, 1),
            new GameField(46, 2, 0, 0, 0, 1),
            new GameField(47, 1, 0, 0, 0, 1),
            //hrač2 start, cil yellow2
            new GameField(50, -4, 5, 2, 1, 2),
            new GameField(51, -4, 4, 2, 2, 2),
            new GameField(52, -5, 5, 2, 3, 2),
            new GameField(53, -5, 4, 2, 4, 2),
            new GameField(54, 0, 4, 0, 0, 2),
            new GameField(55, 0, 3, 0, 0, 2),
            new GameField(56, 0, 2, 0, 0, 2),
            new GameField(57, 0, 1, 0, 0, 2),
            //hrač3 start, cil green3
            new GameField(60, 4, -5, 3, 1, 3),
            new GameField(61, 4, -4, 3, 2, 3),
            new GameField(62, 5, -5, 3, 3, 3),
            new GameField(63, 5, -4, 3, 4, 3),
            new GameField(64, 0, -4, 0, 0, 3),
            new GameField(65, 0, -3, 0, 0, 3),
            new GameField(66, 0, -2, 0, 0, 3),
            new GameField(67, 0, -1, 0, 0, 3),
            //hrač4 start, cil blue4
            new GameField(70, -5, -4, 4, 1, 4),
            new GameField(71, -4, -4, 4, 2, 4),
            new GameField(72, -5, -5, 4, 3, 4),
            new GameField(73, -4, -5, 4, 4, 4),
            new GameField(74, -4, 0, 0, 0, 4),
            new GameField(75, -3, 0, 0, 0, 4),
            new GameField(76, -2, 0, 0, 0, 4),
            new GameField(77, -1, 0, 0, 0, 4),
    };

}