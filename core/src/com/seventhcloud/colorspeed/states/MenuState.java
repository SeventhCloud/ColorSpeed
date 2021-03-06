package com.seventhcloud.colorspeed.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.seventhcloud.colorspeed.ColorSpeed;
import com.seventhcloud.colorspeed.control.MusicManager;
import com.seventhcloud.colorspeed.items.Coin;
import com.seventhcloud.colorspeed.items.Items;
import com.seventhcloud.colorspeed.sprites.ShopScrollPane;

/**
 * Created by WhiteHope on 11.02.2016.
 */
public class MenuState extends State implements Runnable {


    RepeatAction[] repeatMovementAction;

    private Image menuBackground, title;
    private byte numberOfButtons = 3;
    private Array<Actor> menuActors = new Array<Actor>(numberOfButtons);
    private Button startButton;
    private ImageTextButton musicButton, shopButton;
    private String[] buttonNames = {"start", "music", "shop"};

    private Skin skin;
    private InputMultiplexer inputMultiplexer;
    private MoveByAction switchLeft;
    private float switchDuration = 1;


    public MenuState(GameStateManager gsm, AssetManager assetManager) {
        super(gsm, assetManager);

        skin = assetManager.get("menuState/skin/skin.json");

        menuBackground = new Image(assetManager.get("background.png", Texture.class));
        menuBackground.setSize(cam.viewportWidth, cam.viewportHeight);

        MusicManager.setMusic(assetManager.get("music/music" + 0 + ".mp3", Music.class));

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
        initializeMenuActors();
        addMenuActorsToStage();

    }

    private void initializeMenuActors() {

        float titleWidth, titleHeight, startButtonWidth, startButtonHeight;
        FreeTypeFontParameter fontParameter = new FreeTypeFontParameter();

        titleWidth = cam.viewportWidth / 1.2f;
        titleHeight = ColorSpeed.getTextureHeight(titleWidth, null, skin.getRegion("title"));
        startButtonWidth = pixelPerCentimeterX * 1.5f;
        startButtonHeight = startButtonWidth;

        title = new Image(skin.getRegion("title"));
        title.setBounds(cam.viewportWidth / 2 - titleWidth / 2, cam.viewportHeight - titleHeight - 50, titleWidth, titleHeight);
        startButton = new Button(skin, "start");
        startButton.setBounds(cam.viewportWidth / 2 - startButtonWidth / 2, title.getY() - pixelPerCentimeterY * 1.5f - startButtonHeight, startButtonWidth, startButtonHeight);
        musicButton = new ImageTextButton(MusicManager.getCurrentMusic(), skin);
        musicButton.setBounds(50, 50, cam.viewportWidth / 2, ColorSpeed.getTextureHeight(cam.viewportWidth / 2, null, skin.getRegion("music_button_normal")));
        //musicButton.setDebug(false);
        shopButton = new ImageTextButton("Shop!", skin);
        shopButton.setBounds(cam.viewportWidth - musicButton.getWidth(), startButton.getY() - pixelPerCentimeterY * 2, musicButton.getWidth(), musicButton.getHeight());
        sortActors();
        initActions();
    }

    private void sortActors() {

        menuActors.add(startButton);
        menuActors.add(musicButton);
        menuActors.add(shopButton);
    }


    @Override
    protected void handleInput() {

        if (Gdx.input.justTouched()) {

            float inputX = Gdx.input.getX();
            float inputY = cam.viewportHeight - Gdx.input.getY();

            if (inputX >= startButton.getX() && inputX <= startButton.getX() + startButton.getWidth()
                    && inputY >= startButton.getY() && inputY <= startButton.getY() + startButton.getHeight()) {
                gsm.set(new PlayState(gsm, assetManager));
            }

            if (containsRectangle(inputX, inputY, musicButton)) {
                MusicManager.changeMusic(assetManager);
                musicButton.setText(MusicManager.getCurrentMusic());
            }

            if (containsRectangle(inputX, inputY, shopButton)) {
                switchToShop();
            }
        }
    }

    private void switchToShop() {
        stage.addAction(Actions.sequence(switchLeft, Actions.run(this)));
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        batch.setProjectionMatrix(cam.combined);

        batch.begin();
        menuBackground.draw(batch, 1f);
        title.draw(batch, 1f);
        batch.end();

        stage.draw();
    }


    private void initActions() {
        float previousX, previousY, moveByX, moveByY, duration;
        repeatMovementAction = new RepeatAction[menuActors.size];

        //init Animations of Menu Actors and setting it up
        for (byte i = 0; i < menuActors.size; i++) {

            menuActors.get(i).setOrigin(menuActors.get(i).getWidth() / 2, menuActors.get(i).getHeight() / 2);
            previousX = menuActors.get(i).getX();
            previousY = menuActors.get(i).getY();
            moveByX = menuActors.get(i).getWidth() / 20;
            moveByY = menuActors.get(i).getHeight() / 7;
            duration = 0.3f;

            repeatMovementAction[i] = Actions.forever(Actions.sequence(Actions.moveBy(-moveByX, -moveByY, duration), Actions.moveBy(moveByX, moveByY, duration)));
            menuActors.get(i).addAction(repeatMovementAction[i]);
            menuActors.get(i).setName(buttonNames[i]);
        }
        switchLeft = Actions.moveBy(-cam.viewportWidth, 0, switchDuration);
    }

    private void addMenuActorsToStage() {
        for (byte i = 0; i < menuActors.size; i++) {
            stage.addActor(menuActors.get(i));
        }

    }

    protected Skin getSkin() {
        return this.skin;
    }

    private float getCenterX(Actor actor) {
        return actor.getX() + actor.getWidth() / 2;
    }

    private float getCenterY(Actor actor) {
        return actor.getY() + actor.getHeight() / 2;
    }

    @Override
    public void dispose() {

        menuActors.clear();
        stage.dispose();
        inputMultiplexer.clear();

    }

    @Override
    public void run() {
        //dispose();
        gsm.push(new Shop(gsm, this));
        Gdx.app.debug("1 MAL", "");
    }


    public class Shop extends State {

        private ShopScrollPane scrollPane;
        private Image shopSlot;
        private Items items;
        private Coin coin;
        private Label priceField;
        private MenuState menuState;
        private ImageTextButton buyButton;

        private Shop(GameStateManager gsm, MenuState menuState) {
            super(gsm, menuState.assetManager);
            this.menuState = menuState;
            items = new Items(assetManager.get("items/items.atlas", TextureAtlas.class));
            shopSlot = new Image(menuState.getSkin().getRegion("shopSlot"));
            shopSlot.setSize(cam.viewportWidth / 4, cam.viewportWidth / 4);
            shopSlot.setPosition(cam.viewportWidth / 2 - shopSlot.getWidth() / 2, cam.viewportHeight / 2 - shopSlot.getHeight() / 2);
            initializeStage();
        }

        private void initializeStage() {


            coin = new Coin(new TextureRegion((Texture) assetManager.get("coinSpriteSheet.png")), shopSlot.getX() - shopSlot.getWidth(), cam.viewportHeight * 0.7f, shopSlot.getWidth() / 4);
            priceField = new Label("" + items.getItems().get(0).getPrice(), menuState.getSkin(), "pricefield");
            priceField.setBounds(coin.getX() + coin.getWidth() * 1.5f, coin.getY(), musicButton.getWidth() / 2, coin.getHeight());
            buyButton = new ImageTextButton("BUY!", skin);
            buyButton.setSize(shopSlot.getWidth() * 2.5f, 100);
            buyButton.setPosition(cam.viewportWidth / 2 - buyButton.getWidth() / 2, cam.viewportHeight / 10);

            scrollPane = new ShopScrollPane(this);
            scrollPane.addItems(items.getItems());

            scrollPane.setBounds(0, cam.viewportHeight / 2 - cam.viewportHeight / 8, cam.viewportWidth, cam.viewportHeight / 4);

            stage.addActor(shopSlot);
            stage.addActor(scrollPane);
            stage.addActor(coin);
            stage.addActor(priceField);
            stage.addActor(buyButton);

            stage.addAction(Actions.sequence(Actions.moveBy(cam.viewportWidth,0),Actions.moveBy(-cam.viewportWidth, 0, switchDuration)));
            inputMultiplexer.addProcessor(stage);
        }

        public Image getShopSlot() {
            return shopSlot;
        }


        @Override
        protected void handleInput() {

            if (Gdx.input.justTouched()) {
                float inputX = Gdx.input.getX();
                float inputY = cam.viewportHeight - Gdx.input.getY();

                if (scrollPane.isUntouched() && containsRectangle(inputX, inputY, buyButton)) {
                    scrollPane.deleteItemOnSlot();
                    Gdx.app.debug("BLA","");
                }


            }

        }

        @Override
        public void update(float delta) {
            stage.act(delta);

            if (scrollPane.isUntouched()) {
                priceField.setText(scrollPane.getItemOnShopSlot().getPrice() + " $");
            }
        }

        @Override
        public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
            batch.begin();
            menuBackground.draw(batch, 1f);
            title.draw(batch, 1f);
            batch.end();

            stage.draw();

        }

        @Override
        public void dispose() {

        }
    }
}