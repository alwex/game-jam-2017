package com.alwex.ggj.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.alwex.ggj.JamGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.vSyncEnabled = true;
        config.foregroundFPS = -1;
        config.backgroundFPS = -1;

        new LwjglApplication(new JamGame(), config);
    }
}
