package com.seventhcloud.colorspeed.items;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.seventhcloud.colorspeed.control.Animator;

/**
 * Created by WhiteHope on 23.10.2016.
 */
public class Coin extends Actor {

    private byte rows = 1, cols = 8;
    private TextureRegion[] coinFrames;
    private Animation animation;
    private float stateTime;
    private TextureRegion currentFrame;
    private float alpha = 1;
    private AlphaAction alphaAction;
    private float fadeOutSpeed;

    public Coin(TextureRegion coinSpriteSheet, float x, float y, float size){


        coinFrames = Animator.createAnimation(coinSpriteSheet,rows,cols);
        currentFrame = coinSpriteSheet;
        animation = new Animation(0.1f,coinFrames);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        this.currentFrame = coinFrames[1];

        setPosition(x,y);
        setSize(size,size);

        alphaAction = Actions.fadeIn(1);
    }

    public void act (float delta){
        super.act(delta);

        stateTime += delta;
        currentFrame = (TextureRegion) animation.getKeyFrame(stateTime);
    }

    public void draw (Batch batch, float parentAlpha) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha * alpha);
        batch.draw(currentFrame,getX(),getY(),getWidth(),getHeight());
    }

    public void setAlpha(float alpha){
        this.alpha = alpha;
    }

    public TextureRegion getCurrentFrame(){return currentFrame;}
}
