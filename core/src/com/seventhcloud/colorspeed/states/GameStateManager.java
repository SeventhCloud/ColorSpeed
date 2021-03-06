package com.seventhcloud.colorspeed.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Stack;

/**
 * Created by WhiteHope on 11.02.2016.
 */
public class GameStateManager {

    private Stack<State> states;

    public GameStateManager() {
        states = new Stack<State>();

    }

    public void push(State state) {

        states.push(state);
    }

    public void pop() {

        states.pop();
    }

    public void set(State state) {
        states.pop();
        states.push(state);
    }


    public void update(float dt) {
        states.peek().update(dt);
    }

    public void handleInput(){
        states.peek().handleInput();
    }


    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {

        states.peek().render(batch, shapeRenderer);
    }

}
