package com.seventhcloud.colorspeed.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by WhiteHope on 26.10.2016.
 */
public class YellowPotion extends ConsumableItem {


    public YellowPotion(TextureRegion itemTexture, int id, String name, int price) {
        super(itemTexture, id, name, price);
    }

    @Override
    public void consume() {

    }
}