package com.seventhcloud.colorspeed.sprites;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.seventhcloud.colorspeed.ColorSpeed;
import com.seventhcloud.colorspeed.control.Animator;


/**
 * Created by WhiteHope on 22.02.2016.
 */
public class Character extends Actor {

    private byte rows = 1, cols = 4, heartRows = 1, heartCols = 4;
    private int animationnumber = rows * cols;
    private float centerX, centerY;
    private byte heartNumber = 3;
    private Array<Image> hearts = new Array<Image>(heartNumber);
    private int coins;
    private int score;
    private int highScore;


    private  transient TextureRegion character;
    private  transient Animation animation;
    private  transient float stateTime = 0;
    private  transient TextureRegion playerFrames[];

    public Character(float x, float y, float width, float height, Skin skin) {

        TextureRegion heartSheet;
        String playerSprite = "player_sprite";
        String heartSprite = "heart_sprite_sheet";

        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        setBounds(x,y,width,height);
        centerX = x - width / 2;
        centerY = y - height / 2;
        heartSheet = skin.getRegion(heartSprite);

        playerFrames = Animator.createAnimation(skin.getRegion(playerSprite),rows,cols);
        animation = new Animation(0.1f, playerFrames);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        character = playerFrames[0];

        TextureRegion[][] tmp;
        tmp = heartSheet.split(heartSheet.getRegionWidth() / heartCols, heartSheet.getRegionHeight()/ heartRows);

        for (byte i = 0; i < heartNumber; i++) {
            hearts.add(new Heart(tmp[0][i]));

            hearts.get(i).setSize(Gdx.graphics.getWidth()/ 9,(ColorSpeed.getTextureHeight(Gdx.graphics.getWidth()/9,null,tmp[0][i])));
            hearts.get(i).setPosition(i * hearts.get(i).getWidth() + ((i + 1) * hearts.get(i).getWidth() / 2.7f), Gdx.graphics.getHeight() - hearts.get(i).getHeight() - Gdx.graphics.getPpcY() * 0.1f);

        }

        initHeartAction();


    }

    public void act(float delta) {
        super.act(delta);

        stateTime += delta;
        character = (TextureRegion) animation.getKeyFrame(stateTime);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(character, getX(), getY(), getWidth(), getHeight());
    }

    private void initHeartAction() {

        float previousX, previousY, goToX, goToY, duration;
        RepeatAction repeatMovementAction;

        for (Image heart : hearts) {

            previousX = heart.getX();
            previousY = heart.getY();
            goToX = heart.getX() - heart.getWidth() / 6;
            goToY = heart.getY() - heart.getHeight() / 6;
            duration = 0.3f;

            repeatMovementAction = Actions.forever(Actions.sequence(Actions.moveTo(goToX, goToY, duration), Actions.moveTo(previousX, previousY, duration)));


            heart.addAction(repeatMovementAction);
        }

    }

    public void increaseScore(){
        score++;
    }


    public Array<Image> getHearts() {

        return hearts;
    }


    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public int getScore(){ return score; }

    public void save(){
        setStage(null);
        setParent(null);

        Json json = new Json();
        Gdx.app.debug("",json.prettyPrint(this));
    }

}

class Heart extends Image{

    public Heart(TextureRegion heartTexture){
        super(heartTexture);

    }


}