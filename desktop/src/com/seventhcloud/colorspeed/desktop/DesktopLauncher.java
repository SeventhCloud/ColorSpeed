package com.seventhcloud.colorspeed.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.seventhcloud.colorspeed.ColorSpeed;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 1920/2;
		config.width = 1080/2;
		new LwjglApplication(new ColorSpeed(), config);
	}
}
