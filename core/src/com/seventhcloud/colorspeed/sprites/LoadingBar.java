package com.seventhcloud.colorspeed.sprites;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by WhiteHope on 02.10.2016.
 */
public class LoadingBar{

    private Image loadingBarBg;
    private Image loadingBarFg;
    private float percent = 0;
    float loadingBarMarginX, loadingBarMarginY;

    public LoadingBar(float x, float y, float width, float height, float fgMarginX, float fgMarginY, Skin skin) {
        super();

        this.loadingBarMarginX = fgMarginX;
        this.loadingBarMarginY = fgMarginY;
        loadingBarBg = new Image(skin.getRegion("loading_bar_bg"));
        loadingBarFg = new Image(skin.getRegion("loading_bar_fg"));

        loadingBarBg.setBounds(x,y,width, height);

        loadingBarMarginX = loadingBarMarginX / skin.getRegion("loading_bar_bg").getRegionWidth() * loadingBarBg.getWidth();
        loadingBarMarginY = loadingBarMarginY / skin.getRegion("loading_bar_bg").getRegionHeight() * loadingBarBg.getHeight();

        loadingBarFg.setBounds(loadingBarBg.getX() + loadingBarMarginX,loadingBarBg.getY() + loadingBarMarginY,
                0, (skin.getRegion("loading_bar_fg").getRegionHeight()*loadingBarBg.getHeight()) / skin.getRegion("loading_bar_bg").getRegionHeight());
    }

    public boolean update(AssetManager assetManager) {

            percent = MathUtils.lerp(percent, assetManager.getProgress(), 0.1f);
            loadingBarFg.setWidth((loadingBarBg.getWidth() - loadingBarMarginX * 2) * percent);
            return assetManager.update();
    }


    public Image getLoadingBarBg() {
        return loadingBarBg;
    }

    public Image getLoadingBarFg() {
        return loadingBarFg;
    }
}
