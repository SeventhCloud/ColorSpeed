package com.seventhcloud.colorspeed.states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
// import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.seventhcloud.colorspeed.ColorSpeed;
import com.seventhcloud.colorspeed.control.SpawnGenerator;
import com.seventhcloud.colorspeed.sprites.*;
import com.seventhcloud.colorspeed.sprites.Character;

/**
 * Created by WhiteHope on 11.02.2016.
 */
public class PlayState extends State {


    private byte buttonMarginX = 10;
    private byte buttonMarginY = 40;
    private byte numberOfButtons = 4;

    private Skin skin;
    private BitmapFont font;

  //  private Array<PooledEffect> explosionEffects = new Array<PooledEffect>(12);

    private Character character;
    private String actualColor;
    private TextureRegion actualColorTexture;
    private Texture playBackground;
    private String[] colors = {"red", "yellow", "blue", "lilac"};
    private Array<ColorButton> buttons = new Array<ColorButton>(numberOfButtons);
    private Array<Enemy> enemys = new Array<Enemy>();
    private SpawnGenerator spawnGenerator;

    private Sound buttonChange;


    public PlayState(GameStateManager gsm, AssetManager assetManager) {
        super(gsm, assetManager);

        skin = assetManager.get("playState/skin/playStateSkin.json");
        playBackground = assetManager.get("background.png", Texture.class);
        actualColorTexture = skin.getRegion("actual_color_red");
        actualColor = colors[0];

        buttonChange = assetManager.get("sound/button/buttonChange.wav", Sound.class);


        float buttonWidth = cam.viewportWidth / numberOfButtons, buttonHeight = ColorSpeed.getTextureHeight(buttonWidth, null, skin.getRegion("red_button"));
        float characterWidth = pixelPerCentimeterX / 3, characterHeight = characterWidth;
        FreeTypeFontParameter fontParameter = new FreeTypeFontParameter();

        fontParameter.size = (int) pixelPerCentimeterX / 2;
        fontParameter.borderWidth = 1;
        fontParameter.borderColor.set(0, 0, 0, .75f);
        fontParameter.color.set(1, 1, 1, 1);
        font = assetManager.get("fonts/ninePin.ttf", FreeTypeFontGenerator.class).generateFont(fontParameter);

        character = new Character(cam.viewportWidth / 2 - characterWidth / 2, cam.viewportHeight / 2, characterWidth, characterHeight, skin);
        stage.addActor(character);


        for (byte i = 0; i < numberOfButtons; i++) {

            buttons.add(new ColorButton(i * buttonWidth + buttonMarginX, buttonMarginY, buttonWidth - buttonMarginX, buttonHeight, skin, colors[i], skin.getAtlas().createSprite("actual_color_"+colors[i]), cam, colors[i]));
        }

        for (Image heart : character.getHearts()) {
            stage.addActor(heart);
        }

        for (ColorButton colorButton : buttons) {
            stage.addActor(colorButton);
        }

        spawnGenerator = new SpawnGenerator(this);

    }


    @Override
    protected void handleInput() {

        if (Gdx.input.justTouched()) {

            int x = Gdx.input.getX();
            int y = (int) (cam.viewportHeight - Gdx.input.getY());

            for (ColorButton colorButton : buttons) {
                if (colorButton.contains(x, y)) {
                    buttonChange.play();
                    actualColorTexture = colorButton.getActualColorSprite();
                    actualColor = colorButton.getStringColor();
                }
            }


            for (Enemy enemy : enemys) {
                if (enemy.contains(x, y, 2) && enemy.getStringColor().equals(actualColor)) {
                    destroyEnemy(enemy);
                    character.increaseScore();
                }
            }
            // character.save();
        }
    }


    @Override
    public void update(float delta) {


        stage.act(delta);

        for (Enemy enemy : enemys) {
            enemy.update(delta);
        }

      /*  for (PooledEffect explosion : explosionEffects) {
            explosion.update(delta);

            if (explosion.isComplete()) {
                explosion.free();
                explosionEffects.removeValue(explosion, false);
            }
        }
       */
        colissionDedection();

    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        shapeRenderer.setAutoShapeType(true);


         shapeRenderer.setProjectionMatrix(cam.combined);

        batch.begin();
        batch.draw(playBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(actualColorTexture, buttons.get(0).getActualColorSprite().getX(), buttons.get(0).getActualColorSprite().getY(), buttons.get(0).getActualColorSprite().getWidth(), buttons.get(0).getActualColorSprite().getHeight());


        for (Enemy enemy : enemys) {
                enemy.draw(batch);
            }

        font.draw(batch, String.valueOf(character.getScore()), cam.viewportWidth / 2 - character.getWidth() / 2, buttons.get(0).getActualColorSprite().getY() + font.getScaleY());

        /*
        for (PooledEffect enemyExplosionEffect : explosionEffects) {
            enemyExplosionEffect.draw(batch);
        }
        */

        batch.end();


        stage.draw();


        shapeRenderer.begin();
        shapeRenderer.setColor(1,0,0,1);
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        for (Enemy enemy : enemys) {
            shapeRenderer.rect(enemy.getX(),enemy.getY(),enemy.getWidth(),enemy.getHeight());
        }
        shapeRenderer.end();

    }

    private void colissionDedection() {

        for (Enemy enemy : enemys) {
            if (enemy.contains(character.getCenterX(), character.getCenterY(), (int) -character.getWidth())) {
                destroyEnemy(enemy);
                destroyHeart();
                if (character.getHearts().size == 0) {
                    dispose();
                    gsm.set(new MenuState(gsm, assetManager));
                }
            }
        }
    }

    private void destroyEnemy(Enemy enemy) {
        spawnGenerator.destroyEnemy(enemy/*, explosionEffects*/);
        enemys.removeValue(enemy, false);
    }

    private void destroyHeart() {
        spawnGenerator.destroyHeart(/*explosionEffects*/);
        stage.getActors().removeIndex(character.getHearts().pop().getZIndex());
    }

    @Override
    public void dispose() {

     /*   for (byte i = 0; i < buttonTextures.length; i++) {
            buttonTextures[i].dispose();
            actualColorTextures[i].dispose();
        }*/

        stage.dispose();

        /* enemyPool.clear();
        explosionEffects.clear();
        enemyExplosionPool.clear();
        buttons.clear();
        enemys.clear();
        timer.clear();

*/
    }

    public Character getCharacter() {
        return character;
    }

    public void addEnemy(Enemy enemy) {
        enemys.add(enemy);
    }

    public Stage getStage(){return stage;}

}
