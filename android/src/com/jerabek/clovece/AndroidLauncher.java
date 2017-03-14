package com.jerabek.clovece;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		initialize(new CloveceNezlobSe(), config);

		/*
		setContentView(R.layout.layout);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		// Create libgdx fragment
		GameFragment libgdxFragment = new GameFragment();

		// Put it inside the framelayout (which is defined in the layout.xml file).
		getSupportFragmentManager().beginTransaction().
				add(R.id.content_framelayout, libgdxFragment).
				commit();
		*/
	}

	@Override
	public void exit() {

	}

}
