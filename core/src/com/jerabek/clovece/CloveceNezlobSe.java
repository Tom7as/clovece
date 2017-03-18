package com.jerabek.clovece;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CloveceNezlobSe extends ApplicationAdapter {

	public static final float appWidth = 1080;
	public static final float appHeight = 1080;

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
		//Gdx.gl.glClearColor(1, 0, 0, 1);

		gsm.push(new MenuState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1f, 0.89f, 0.59f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	@Override
	public void dispose() {
		super.dispose();
		//music.dispose();
	}



}
