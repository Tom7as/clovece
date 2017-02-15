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

    private Stage stage;
    private float zoom = 70, fieldSize = 80, smallFieldSize = fieldSize*0.8f;
    private GameField[] data = GameField.getData();

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


    private void refreshBoard(SpriteBatch sb) {
        for (GameField aData : data) {
//            aData.getSequence();
//            aData.getColor();

            if (aData.getSequence() < 40) {
                sb.draw(fieldImg[aData.getColor()], cam.position.x + aData.getX() * zoom - fieldSize * 0.5f, cam.position.y + aData.getY() * 70 - fieldSize * 0.5f);
            } else {
                //fieldImg(data[i].getX(), data[i].getY());
                sb.draw(fieldImg[aData.getColor()], cam.position.x + aData.getX() * zoom - smallFieldSize * 0.5f, cam.position.y + aData.getY() * 70 - smallFieldSize * 0.5f, smallFieldSize, smallFieldSize);
            }
        }
    }

    @Override
    public void dispose() {
        for(int i = 0; i < data.length; i++){
            fieldImg[i].dispose();
        }
        System.out.println("Menu State Disposed");
    }
}
