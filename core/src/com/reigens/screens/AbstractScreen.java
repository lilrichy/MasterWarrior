package com.reigens.screens;

/**
 * Created by Rich on 8/12/2014.
 */

import com.badlogic.gdx.Screen;
import com.reigens.MasterWarrior;

/**
 * @author Mats Svensson
 */
public abstract class AbstractScreen implements Screen {

    protected MasterWarrior game;

    public AbstractScreen(MasterWarrior game) {
        this.game = game;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}