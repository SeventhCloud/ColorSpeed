package com.seventhcloud.colorspeed.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by WhiteHope on 15.10.2016.
 */
public abstract class ConsumableItem extends Item {


    public ConsumableItem(TextureRegion itemTexture, int id, String name, int price){
        super(itemTexture, id, name, price);

    }

    public abstract void consume();

}
