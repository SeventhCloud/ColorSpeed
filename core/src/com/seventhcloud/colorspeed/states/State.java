package com.seventhcloud.colorspeed.states;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by WhiteHope on 11.02.2016.
 */

public abstract class State {

    protected OrthographicCamera cam;
    protected Stage stage;
    protected Vector3 mouse;
    protected GameStateManager gsm;
    protected AssetManager assetManager;
    protected float pixelPerCentimeterX = Gdx.graphics.getPpcX();
    protected float pixelPerCentimeterY = Gdx.graphics.getPpcY();



    protected State(GameStateManager gsm, AssetManager assetManager) {


        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        this.gsm = gsm;
        this.assetManager = assetManager;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(new ScreenViewport(cam));
        mouse = new Vector3();



    }



    protected abstract void handleInput();

    public abstract void update(float delta);

    public abstract void render(SpriteBatch batch, ShapeRenderer shapeRenderer);

    public abstract void dispose();

    public OrthographicCamera getCam(){
        return cam;
    }

    protected boolean containsRectangle(float inputX, float inputY, Actor actor) {
        return actor.getX() <= inputX && actor.getX() + actor.getWidth() >= inputX && actor.getY() <= inputY && actor.getY() + actor.getHeight() >= inputY;
    }

    public AssetManager getAssetManager(){
        return assetManager;
    }
}

