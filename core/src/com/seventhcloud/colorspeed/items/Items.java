package com.seventhcloud.colorspeed.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

/**
 * Created by WhiteHope on 15.10.2016.
 */
public class Items {

    private Array<Item> items;
    private JsonValue jsonItems;
    private TextureAtlas atlas;
    private FileHandle file = Gdx.files.local("bin/bra.txt");
    public Items(TextureAtlas atlas) {
        this.atlas = atlas;
        items = new Array<Item>();
        jsonItems = new JsonReader().parse(Gdx.files.internal("items/items.json")).get("items");

        for (JsonValue wearableItem : jsonItems.get("wearableItems")) {
            items.add(new WearableItem(atlas.findRegion(wearableItem.name), wearableItem.getInt("id"), wearableItem.name, wearableItem.getInt("price")));
           // Gdx.app.debug("NAMES", wearableItem.name());
        }
        addPotionSet(jsonItems);

    }

    private void addPotionSet(JsonValue jsonItems) {
        Json json = new Json(JsonWriter.OutputType.json);
        //json.setWriter(JsonWriter.OutputType.json);
        //Gdx.app.debug("",json.toJson(new RedPotion(atlas.findRegion("redPotionSmall"),1,"redPotion",300),RedPotion.class));
     //   Gdx.app.debug("",json.prettyPrint(new RedPotion(atlas.findRegion("redPotionSmall"),1,"redPotion",300)));
        //new Item(atlas.findRegion("redPotionSmall"),1,"redPotion",300).write(json);
        //file.writeString(json.prettyPrint(new RedPotion(atlas.findRegion("redPotionSmall"),1,"redPotion",300)),false);
       // for (JsonValue consumableItem : jsonItems.get("consumableItems"))
        //items.add(new RedPotion(atlas.findRegion(consumableItem.name), consumableItem.getInt("id"), consumableItem.name(), consumableItem.getInt("price")));

    }

    public Array<Item> getItems() {
        return items;
    }

}
