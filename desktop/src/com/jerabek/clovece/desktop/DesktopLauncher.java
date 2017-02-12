package com.jerabek.clovece.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jerabek.clovece.CloveceNezlobSe;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 1000;
		config.height = 800;
		config.title = CloveceNezlobSe.TITLE;
		new LwjglApplication(new CloveceNezlobSe(), config);
	}
}
