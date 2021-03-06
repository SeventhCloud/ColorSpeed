package com.seventhcloud.colorspeed.states;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
// import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.seventhcloud.colorspeed.ColorSpeed;
import com.seventhcloud.colorspeed.sprites.LoadingBar;


/**
 * Created by WhiteHope on 27.09.2016.
 */
public class LoadingState extends State {

    private byte maxMusic = 6;
    private byte maxExplodeSound = 4;
    private String[] colors = {"red", "yellow", "blue", "lilac"};
    private LoadingBar loadingBar;

    float loadingBarMarginX = 4, loadingBarMarginY = 4;

    protected LoadingState(GameStateManager gsm, AssetManager assetManager) {
        super(gsm, assetManager);

        Skin skin;
        skin = assetManager.get("loadingState/skin/loadingStateSkin.json", Skin.class);

       loadingBar = new LoadingBar(cam.viewportWidth / 2 - (cam.viewportWidth - 60) / 2,cam.viewportHeight / 4,
                cam.viewportWidth - 60,ColorSpeed.getTextureHeight(cam.viewportWidth - 60, null, skin.getRegion("loading_bar_bg")),4,4,skin);



     /*   loadingBarBg.setSize(cam.viewportWidth - 60, ColorSpeed.getTextureHeight(cam.viewportWidth - 60, null, skin.getRegion("loading_bar_bg")));
        loadingBarBg.setPosition(cam.viewportWidth / 2 - loadingBarBg.getWidth() / 2, cam.viewportHeight / 4);

        loadingBarMarginX = loadingBarMarginX / skin.getRegion("loading_bar_bg").getRegionWidth() * loadingBarBg.getWidth();
        loadingBarMarginY = loadingBarMarginY / skin.getRegion("loading_bar_bg").getRegionHeight() * loadingBarBg.getHeight();
        loadingBarFg.setSize(0, (skin.getRegion("loading_bar_fg").getRegionHeight()*loadingBarBg.getHeight()) / skin.getRegion("loading_bar_bg").getRegionHeight());
        loadingBarFg.setX(loadingBarBg.getX() + loadingBarMarginX);
        loadingBarFg.setY(loadingBarBg.getY() + loadingBarMarginY);
*/

        //TEST



        queueAssets();

    }

    private void queueAssets(){
        assetManager.load("menuState/skin/skin.json", Skin.class, new SkinLoader.SkinParameter("menuState/skin/menuState.atlas"));
        assetManager.load("playState/skin/playStateSkin.json", Skin.class, new SkinLoader.SkinParameter("playState/skin/playStateTextures.atlas"));
        assetManager.load("background.png", Texture.class);
        assetManager.load("sound/button/buttonChange.wav", Sound.class);

        for(byte i = 0; i < maxMusic; i++){
            assetManager.load("music/music" + i + ".mp3", Music.class);
        }

        for(byte i = 0; i < maxExplodeSound; i++){
            assetManager.load("sound/enemy/enemyExplode" + i + ".wav", Sound.class);
        }

       // assetManager.load("playState/enemys/effects/explosion_effect.p", ParticleEffect.class);
       // assetManager.load("playState/character/effects/heart_explosion.p", ParticleEffect.class);
        assetManager.load("fonts/ninePin.ttf", FreeTypeFontGenerator.class);
        assetManager.load("coinSpriteSheet.png", Texture.class);
        assetManager.load("items/items.atlas", TextureAtlas.class);

    }


    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float delta) {


        if(loadingBar.update(assetManager)){
            gsm.set(new MenuState(gsm, assetManager));
        }
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        batch.begin();
        loadingBar.getLoadingBarBg().draw(batch,1f);
        loadingBar.getLoadingBarFg().draw(batch,1f);
        batch.end();
    }

    @Override
    public void dispose() {

    }
}
