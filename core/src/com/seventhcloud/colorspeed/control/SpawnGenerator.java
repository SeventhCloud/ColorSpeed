package com.seventhcloud.colorspeed.control;

import com.badlogic.gdx.graphics.OrthographicCamera;
// import com.badlogic.gdx.graphics.g2d.ParticleEffect;
// import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
// import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.seventhcloud.colorspeed.sprites.*;
import com.seventhcloud.colorspeed.sprites.Character;
import com.seventhcloud.colorspeed.states.PlayState;

/**
 * Created by WhiteHope on 09.06.2016.
 */
public class SpawnGenerator {

    private Pool<Enemy> enemyPool = new Pool<Enemy>() {

        byte soundIndex = -1;

        @Override
        protected Enemy newObject() {

            soundIndex++;


            if (soundIndex > 3)
                soundIndex = 0;

            return new Enemy(soundIndex, playState.getAssetManager(),playState.getStage());
        }
    };
    private PlayState playState;
    private Character character;
    private OrthographicCamera cam;
    private String[] colors = {"red", "yellow", "blue", "lilac"};
    private TextureRegion[] enemyTextures = new TextureRegion[colors.length];

  //  private ParticleEffectPool enemyExplosionPool;
  //  private ParticleEffectPool heartExplosionPool;

    private Timer timer = new Timer();
    private Task spawn;
    private int enemyInterval = 2;

    public SpawnGenerator(PlayState playState){
        this.playState = playState;
        this.character = playState.getCharacter();
        cam = playState.getCam();

        for(byte i = 0; i < enemyTextures.length; i++){
            enemyTextures[i] = playState.getAssetManager().get("playState/skin/playStateSkin.json", Skin.class).getRegion("enemy_"+ colors[i]);
        }

    /*     ParticleEffect enemyExplosionEffect;
        ParticleEffect heartExplosionEffect;

        enemyExplosionEffect = playState.getAssetManager().get("playState/enemys/effects/explosion_effect.p" , ParticleEffect.class);
        enemyExplosionEffect.setEmittersCleanUpBlendFunction(false);
        enemyExplosionPool = new ParticleEffectPool(enemyExplosionEffect, 1, 5);

        heartExplosionEffect = playState.getAssetManager().get("playState/character/effects/heart_explosion.p", ParticleEffect.class);
        heartExplosionEffect.setEmittersCleanUpBlendFunction(false);
        heartExplosionPool = new ParticleEffectPool(heartExplosionEffect,0,5);
    */
        initEnemySpawn();



    }


    public void startSpawning(){
        timer.start();
    }


    private void initEnemySpawn() {

        spawn = new Task() {
            int randomEnemy = 0;
            int randomX = 0;
            int randomY = 0;

            @Override
            public void run() {
                randomEnemy = MathUtils.random(0, 3);
                randomX = (int) MathUtils.random(cam.viewportWidth, cam.viewportWidth * 1.5f);
                randomX = randomX * MathUtils.randomSign();
                randomY = (int) MathUtils.random(40, cam.viewportHeight * 1.2f); // The 40 is MarginY!!!!!!!!

                Enemy enemyTospawn = enemyPool.obtain();
                enemyTospawn.spawnInit(randomEnemy + randomX, randomEnemy + randomY, enemyTextures[randomEnemy], colors[randomEnemy], character);
                playState.addEnemy(enemyTospawn);

            }

        };
        timer.scheduleTask(spawn, 0, enemyInterval);

    }

    public void destroyEnemy(Enemy enemy/*, Array<PooledEffect> explosionEffects*/) {
      /*  PooledEffect effect = enemyExplosionPool.obtain();
        effect.setPosition(enemy.getCenterX(), enemy.getCenterY());
        explosionEffects.add(effect);
      */
        enemyPool.free(enemy);

    }

    public void destroyHeart(/*Array<PooledEffect> explosionEffects*/){
      /*  PooledEffect effect = heartExplosionPool.obtain();
        effect.setPosition(character.getHearts().peek().getX() + character.getHearts().peek().getWidth()/2, character.getHearts().peek().getY()+ character.getHearts().peek().getHeight()/2);
        explosionEffects.add(effect);
        */
    }
}
