package com.jerabek.clovece;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.jerabek.clovece.States.GameStateManager;
import com.jerabek.clovece.States.MenuState;

import java.awt.Point;

public class CloveceNezlobSe extends ApplicationAdapter {
	public static final float appWidth = 1280;
	public static final float appHeight = 720;

	public static final String TITLE = "Clovece nezlob se";
	private GameStateManager gsm;
	private SpriteBatch batch;

	private Music music;


	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		//music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		//music.setLooping(true);
		//music.setVolume(0.1f);
		//music.play();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new MenuState(gsm));


	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);

	}

	@Override
	public void dispose() {
		super.dispose();
		music.dispose();
	}



}
