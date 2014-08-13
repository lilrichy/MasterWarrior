package com.reigens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.reigens.screens.LoadingScreen;
import com.reigens.screens.MainMenu;

public class MasterWarrior extends Game {

    public static final String TITLE = "Master Warrior";
    public static final String VERSION = "0.0.0.2";
    public static final AssetManager manager = new AssetManager();
    MainMenu mainMenu;

    @Override
    public void create() {


        setScreen(new LoadingScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }


}
