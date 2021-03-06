package com.seventhcloud.colorspeed.sprites;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.seventhcloud.colorspeed.ColorSpeed;

/**
 * Created by WhiteHope on 14.02.2016.
 */

public class ColorButton extends Button {

    private RepeatAction repeatMovementAction = new RepeatAction();
    private Sprite actualColorSprite;
    private String color;


    public ColorButton(float x, float y, float width, float height, Skin skin, String styleName, Sprite actualColorSprite, OrthographicCamera cam, String color) {
        super(skin,styleName);

        float actualColorWidth = cam.viewportWidth / 20, actualColorHeight = ColorSpeed.getTextureHeight(actualColorWidth, null, skin.getRegion("actual_color_red"));

        setBounds(x, y, width, height);
        this.actualColorSprite = actualColorSprite;
        actualColorSprite.setSize(cam.viewportWidth / 20, ColorSpeed.getTextureHeight(actualColorWidth, null, skin.getRegion("actual_color_red")));
        actualColorSprite.setPosition(cam.viewportWidth - actualColorWidth - 10, cam.viewportHeight - actualColorHeight - 40 / 2);
        this.color = color;


        initMovementAnimation();

    }

    private void initMovementAnimation() {

        float previousX, previousY, goToX, goToY, duration;

        previousX = getX();
        previousY = getY();
        goToX = getX() - getWidth() / 20;
        goToY = getY() - getHeight() / 7;
        duration = 0.3f;

        repeatMovementAction = Actions.forever(Actions.sequence(Actions.moveTo(goToX, goToY, duration), Actions.moveTo(previousX, previousY, duration)));

        addAction(repeatMovementAction);

    }

    public boolean contains(float x, float y) {
        return getX() <= x && getX() + getWidth() >= x && getY() <= y && getY() + getHeight() >= y;
    }

    public Sprite getActualColorSprite() {
        return actualColorSprite;
    }

    public String getStringColor() {
        return color;
    }
}
