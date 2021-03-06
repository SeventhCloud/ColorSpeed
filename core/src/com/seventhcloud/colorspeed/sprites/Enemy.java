package com.seventhcloud.colorspeed.sprites;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by WhiteHope on 02.05.2016.
 */
public class Enemy extends Actor implements Pool.Poolable {




    private String color;
    private TextureRegion texture;
    private int speed = 3;
    private Vector2 enemyVector = new Vector2();
    private Vector2 fromEnemyToCharacterVector = new Vector2();
    private Sound explodeSound;
    private float pixelPerCentimeter = Gdx.graphics.getPpcX();
    private boolean increasedSecond = false;

    public Enemy(byte soundIndex, AssetManager assetManager, Stage stage) {
        setStage(stage);
        addAction(Actions.forever(Actions.sequence(Actions.scaleBy(20,20,1),Actions.scaleBy(-20,-20,1))));
        setDebug(false);
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        explodeSound = assetManager.get("sound/enemy/enemyExplode" + soundIndex + ".wav", Sound.class);
        setRotation(360);

    }


    public void update(float delta) {

        act(delta);
        float scalar = speed * delta * pixelPerCentimeter / 100;
        //Gdx.app.debug("dsasdsa",""+ scalar);
        fromEnemyToCharacterVector.scl(scalar);

        enemyVector.add(fromEnemyToCharacterVector);

        fromEnemyToCharacterVector.scl(1 / scalar);


         setX(enemyVector.x);
         setY(enemyVector.y);

    }

    public void draw(Batch batch){
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void reset() {
        explodeSound.play();
        setX(0);
        setY(0);
        enemyVector.set(0,0);
        fromEnemyToCharacterVector.set(0,0);
    }

    public void spawnInit(float x, float y, TextureRegion texture, String color, Character character){
        setX(x);
        setY(y);

        this.texture = texture;
        this.color = color;
        setSize(character.getWidth() * 1.8f, character.getWidth() * 1.8f);
        enemyVector.set(x,y);
        fromEnemyToCharacterVector.set(character.getCenterX() - x, character.getCenterY() - y); // determine vector from enemy to vector its for the direction
        fromEnemyToCharacterVector.clamp(100, 100);  // clamp (spanne) vector to 100 because when 2 vectors have same direction but other x y values the will have other speed, to avoid that we must clamp
        increaseSpeed(character);

    }

    private void increaseSpeed(Character character){


        if (!increasedSecond && 4 < character.getScore()){
            speed = speed + 2;
            increasedSecond = true;
        }

    }

    public TextureRegion getEnemyTexture() {
        return texture;
    }


    public float getCenterX(){return getX() + getWidth()/2;}

    public float getCenterY(){return getY() + getWidth()/2;}

    public String getStringColor() {
        return color;
    }


    public boolean contains (float x, float y, int increaseRadius) {
        x = getX() - x;
        y = getY() - y;
        return x * x + y * y <= (getWidth()+ increaseRadius) * (getWidth()+ increaseRadius);
    }
}
