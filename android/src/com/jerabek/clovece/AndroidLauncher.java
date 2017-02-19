package com.jerabek.clovece;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.jerabek.clovece.States.PlayState;

public class AndroidLauncher extends FragmentActivity implements  AndroidFragmentApplication.Callbacks {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout);

		// Create libgdx fragment
		GameFragment libgdxFragment = new GameFragment();

		// Put it inside the framelayout (which is defined in the layout.xml file).
		getSupportFragmentManager().beginTransaction().
				add(R.id.content_framelayout, libgdxFragment).
				commit();
	}

	@Override
	public void exit() {

	}

}
