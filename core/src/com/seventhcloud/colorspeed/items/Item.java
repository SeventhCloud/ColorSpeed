package com.seventhcloud.colorspeed.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Created by WhiteHope on 15.10.2016.
 */
public class Item extends Actor implements Json.Serializable{

    private int id;
    private int price;
    private transient TextureRegion texture;


    public Item(TextureRegion itemTexture,int id, String name, int price){

        this.id = id;
        setName(name);
        this.price = price;
    }

    public int getPrice(){ return price;}

    @Override
    public void write(Json json) {
        json.writeObjectStart();
        json.prettyPrint(this);
        json.writeObjectEnd();
    }

    @Override
    public void read(Json json, JsonValue jsonData) {

    }
}

