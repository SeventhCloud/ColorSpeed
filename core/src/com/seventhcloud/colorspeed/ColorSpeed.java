package com.seventhcloud.colorspeed;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.seventhcloud.colorspeed.control.BetterSkinLoader;
import com.seventhcloud.colorspeed.states.GameStateManager;
import com.seventhcloud.colorspeed.states.SplashState;

public class ColorSpeed extends Game {

	public final static int WIDTH = 1920;
	public final static int HEIGHT = 1080;
	public final static String TITLE = "Color Speed";

	private InputProcessor adapter;
	private GameStateManager gsm;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private AssetManager assetManager;
//    public static FileHandle saveFile = Gdx.files.local("bin/save.json");

	@Override
	public void create() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setColor(1, 0, 0, 1);
		gsm = new GameStateManager();
		assetManager = new AssetManager();
		assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(assetManager.getFileHandleResolver()));
		assetManager.setLoader(Skin.class, new BetterSkinLoader(assetManager.getFileHandleResolver()));
		gsm.push(new SplashState(gsm, assetManager));
		adapter = new InputAdapter(){
			@Override
			public boolean keyDown(int keycode) {

				if ((keycode == Input.Keys.BACK) )
					Gdx.app.exit();
				return false;
			}
		};

		InputMultiplexer multiplexer = new InputMultiplexer(adapter);
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void render() {
		gsm.handleInput();
		gsm.render(batch, shapeRenderer);
		gsm.update(Gdx.graphics.getDeltaTime());

	}

	public static float getTextureHeight(float textureWidth, Texture texture, TextureRegion textureRegion){
		if(texture!= null) {
			return (textureWidth * texture.getTextureData().getHeight() / texture.getTextureData().getWidth());
		}else
			return (textureWidth * textureRegion.getRegionHeight() / textureRegion.getRegionWidth());
	}

}