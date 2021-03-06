package com.seventhcloud.colorspeed.control;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

/**
 * Created by WhiteHope on 18.06.2016.
 */
public class MusicManager {

    private static Music music;
    private static byte musicNumber = 0;
    private static String[] currentMusicTitels = {"Color Speed: Main Music", "Color Speed: Hope", "Color Speed: Unbeatable", "Color Speed: A better World", "Color Speed: Dont give Up", "Color Speed: Warriors"};
    private static byte maxMusic = 6;

    public static void setMusic(Music pMusic){

        if(music!= null)
            music.stop();

        music = pMusic;
        music.setLooping(true);
        music.setVolume(.75f);
        music.play();
    }

    public static void changeMusic(AssetManager assetManager){

        musicNumber++;
        if (musicNumber >= maxMusic) {
            musicNumber = 0;
        }

        music.stop();
        music = assetManager.get("music/music" + musicNumber + ".mp3", Music.class);
        music.setLooping(true);
        music.setVolume(.75f);
        music.play();

    }


    public static String getCurrentMusic(){

        return currentMusicTitels[musicNumber];
    }
}
