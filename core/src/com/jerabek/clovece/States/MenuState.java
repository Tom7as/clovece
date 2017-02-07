package com.jerabek.clovece.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jerabek.clovece.CloveceNezlobSe;

/**
 * Created by Tomas on 07.02.2017.
 */

public class MenuState extends State{
    private Texture background;
    private Texture playBtn;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bg.png");
        playBtn = new Texture("playBtn.png");
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0,0, CloveceNezlobSe.WIDTH, CloveceNezlobSe.HEIGHT);
        sb.draw(playBtn, (CloveceNezlobSe.WIDTH / 2) - (playBtn.getWidth() / 2), CloveceNezlobSe.HEIGHT / 2);
        sb.end();
    }
}
