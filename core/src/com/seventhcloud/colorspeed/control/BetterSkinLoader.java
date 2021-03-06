package com.seventhcloud.colorspeed.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WhiteHope on 24.10.2016.
 */
public class BetterSkinLoader extends SkinLoader {

    private Map<String,BitmapFont> fontsByName;

    public BetterSkinLoader(FileHandleResolver resolver) {
        super(resolver);
        this.fontsByName = initFonts();
    }

    private Map<String,BitmapFont> initFonts() {
        FileHandle fontFile = Gdx.files.internal("fonts/ninePin.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);

        Map<String,BitmapFont> fontsByName = new HashMap<String,BitmapFont>();
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.borderWidth = 2;
        float width = Gdx.graphics.getWidth()/7;
        param.size = Math.round( width / 4);
        fontsByName.put( "huge-font", generator.generateFont( param ));
        param.size = Math.round( width / 5);
        fontsByName.put( "big-font", generator.generateFont( param ));
        param.size = Math.round( width / 6);
        fontsByName.put( "small-font", generator.generateFont( param ));

        generator.dispose();
        return fontsByName;
    }


    @Override
    public Skin loadSync(AssetManager manager, String fileName, FileHandle file, SkinParameter parameter) {

        String textureAtlasPath = file.pathWithoutExtension() + ".atlas";
        ObjectMap<String, Object> resources = null;
        if (parameter != null) {
            if (parameter.textureAtlasPath != null){
                textureAtlasPath = parameter.textureAtlasPath;
            }
            if (parameter.resources != null){
                resources = parameter.resources;
            }
        }
        TextureAtlas atlas = manager.get(textureAtlasPath, TextureAtlas.class);
        Skin skin = new Skin(atlas);

        for( Map.Entry<String,BitmapFont> kv : this.fontsByName.entrySet() ) {

            skin.add( kv.getKey(), kv.getValue() );
        }

        if (resources != null) {
            for (ObjectMap.Entry<String, Object> entry : resources.entries()) {
                skin.add(entry.key, entry.value);
            }
        }
        skin.load(file);
        return skin;
    }
}
