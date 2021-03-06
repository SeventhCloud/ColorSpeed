package com.seventhcloud.colorspeed.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Json;

/**
 * Created by WhiteHope on 25.10.2016.
 */
public class Saver extends Actor implements Disposable {

    private int highscore;
    private int coins;
    Texture texture;

    public void save(Character character){
      //  if(highscore< character.getScore())
      //      highscore = character.getScore();

        texture = new Texture(Gdx.files.internal("background.png"));
        coins = 1;

        Json json = new Json();
        System.out.println(json.prettyPrint(this));
    }


    @Override
    public void dispose() {

    }
    // private Array<Items> items;


}
