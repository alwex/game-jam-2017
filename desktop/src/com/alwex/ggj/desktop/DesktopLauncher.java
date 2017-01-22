package com.alwex.ggj.desktop;

import com.alwex.ggj.JamGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class DesktopLauncher {
    public static void main(String[] arg) {

//        TexturePacker.Settings settings = new TexturePacker.Settings();
//        settings.maxWidth = 512;
//        settings.maxHeight = 512;
//
//        TexturePacker.process(settings, "sprites/sources", "sprites", "atlas");

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.vSyncEnabled = true;
        config.foregroundFPS = -1;
        config.backgroundFPS = -1;

        config.width *= 1.5f;
        config.height *= 1.5f;

        new LwjglApplication(new JamGame(), config);
    }
}
