package com.seventhcloud.colorspeed.control;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by WhiteHope on 23.10.2016.
 */
public class Animator {

    public Animator(){}

    public static TextureRegion[] createAnimation(TextureRegion spriteSheet, short rows, short cols){

        int animationNumber = rows*cols;
        TextureRegion[][] textureRegions = spriteSheet.split(spriteSheet.getRegionWidth()/cols,spriteSheet.getRegionHeight()/rows);
        TextureRegion[] animationFrames = new TextureRegion[animationNumber];
        short i = 0;
        for (short r = 0; r < rows; r++){
            for (short c = 0; c < cols; c++){
               animationFrames[i] = textureRegions[r][c];
                i++;
            }
        }
        return animationFrames;
    }

}
