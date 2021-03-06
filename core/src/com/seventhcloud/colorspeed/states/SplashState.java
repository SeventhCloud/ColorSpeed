package com.seventhcloud.colorspeed.states;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

/**
 * Created by WhiteHope on 03.06.2016.
 */
public class SplashState extends State {


    private Image gameLogo;
    private Image logo;
    private Array<Image> logos = new Array<Image>(10);
    private byte splashDuration = 1;
    private float xDelta = 0;


    public SplashState(GameStateManager gsm, AssetManager assetManager) {
        super(gsm, assetManager);

        gameLogo = new Image(new Texture("splashState/splash_logo.png"));
        logo = new Image(new Texture("splashState/logo.png"));

        gameLogo.setBounds(cam.viewportWidth / 2 - cam.viewportWidth / 3, cam.viewportHeight / 2 - cam.viewportWidth / 3, cam.viewportWidth / 6, cam.viewportWidth / 6 );
        logo.setBounds(20, 20, pixelPerCentimeterX*2, pixelPerCentimeterX*2);

        logos.add(gameLogo);
        logos.add(logo);

        initAnimations(logos);
        assetManager.load("loadingState/skin/loadingStateSkin.json", Skin.class, new SkinLoader.SkinParameter("loadingState/skin/loadingStateTextures.atlas"));
        assetManager.finishLoading();
    }


    @Override
    public void update(float delta) {

        stage.act(delta);
        xDelta += delta;

        if (xDelta > splashDuration){
            dispose();
            gsm.set(new LoadingState(gsm, assetManager));
        }

    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {

        stage.draw();
    }

    @Override
    protected void handleInput() {


    }



    @Override
    public void dispose() {
        stage.dispose();

    }


    private void initAnimations(Array<Image> actors) {

        for(Actor actor : actors){
            actor.addAction(Actions.sequence(Actions.alpha(0), Actions.alpha(1, splashDuration)));
            stage.addActor(actor);
        }

        actors.clear();
    }

}
