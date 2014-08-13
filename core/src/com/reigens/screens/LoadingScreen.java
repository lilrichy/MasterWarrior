package com.reigens.screens;

/**
 * Created by Rich on 8/12/2014.
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.reigens.MasterWarrior;

/**
 * Loading screen author Mats Svensson
 */
public class LoadingScreen extends AbstractScreen {

    private Stage stage;

    private Image logo;
    private Image loadingFrame;
    private Image loadingBarHidden;
    private Image screenBg;
    private Image loadingBg;

    private float startX, endX;
    private float percent;

    private Actor loadingBar;

    public LoadingScreen(MasterWarrior game) {
        super(game);
    }

    @Override
    public void show() {
//		stage.setViewport(new FitViewport(800, 480, new PerspectiveCamera()));

        // Tell the manager to load assets for the loading screen
        //  game.manager.load("loading/loading.pack", TextureAtlas.class);
        game.manager.load("ui/newUI.pack", TextureAtlas.class);
        // Wait until they are finished loading
        game.manager.finishLoading();

        // Initialize the stage where we will place everything
        stage = new Stage();

        // Get our textureatlas from the manager
        //  TextureAtlas atlas = game.manager.get("loading/loading.pack", TextureAtlas.class);
        TextureAtlas atlasUI = game.manager.get("ui/newUI.pack", TextureAtlas.class);

        // Grab the regions from the atlas and create some images
        logo = new Image(atlasUI.findRegion("logo"));
        loadingFrame = new Image(atlasUI.findRegion("progress_bar_frame"));
        loadingBarHidden = new Image(atlasUI.findRegion("progress_bar_layer"));
        screenBg = new Image(atlasUI.findRegion("blank_window"));
        loadingBg = new Image(atlasUI.findRegion("progress_bar_background"));
        loadingBar = new Image(atlasUI.findRegion("progress_bar"));

        // Add the loading bar animation
        //  Animation anim = new Animation(0.05f, atlasUI.findRegions("progress_bar_full"));
        // anim.setPlayMode(PlayMode.LOOP_REVERSED);
        //  loadingBar = new LoadingBar(anim);

        // Or if you only need a static bar, you can do
        // loadingBar = new Image(atlas.findRegion("loading-bar1"));

        // Add all the actors to the stage
        stage.addActor(screenBg);
        stage.addActor(loadingBar);
        stage.addActor(loadingBg);
        stage.addActor(loadingBarHidden);
        stage.addActor(loadingFrame);
        stage.addActor(logo);

        // Add everything to be loaded, for instance:
        // game.manager.load("ui/newUI.pack", TextureAtlas.class);
        // game.manager.load("ui/button.pack", TextureAtlas.class);
        // game.manager.load("ui/menuSkin.json", Skin.class);
    }

    @Override
    public void resize(int width, int height) {
        // Scale the viewport to fit the screen
        //Vector2 scaledView = Scaling.fit.apply(800, 480, width, height);
        //stage.getViewport().update((int)scaledView.x, (int)scaledView.y, true);
        stage.getViewport().update(width, height, true);

        // Make the background fill the screen
        screenBg.setSize(width, height);

        // Place the logo in the middle of the screen and 100 px up
        logo.setX((width - logo.getWidth()) / 2);
        logo.setY((height - logo.getHeight()) / 2 + 100);

        // Place the loading frame in the middle of the screen
        loadingFrame.setX((stage.getWidth() - loadingFrame.getWidth()) / 2);
        loadingFrame.setY((stage.getHeight() - loadingFrame.getHeight()) / 4 - loadingFrame.getHeight());

        // Place the loading bar at the same spot as the frame, adjusted a few
        // px
        loadingBar.setX(loadingFrame.getX() + 17);
        loadingBar.setY(loadingFrame.getY() + 21);

        // Place the image that will hide the bar on top of the bar, adjusted a
        // few px

        loadingBarHidden.setX(loadingBar.getX() + 90);
        loadingBarHidden.setY(loadingBar.getY() + 1.5f);
        // The start position and how far to move the hidden loading bar
        startX = loadingBarHidden.getX();
        endX = 450;

        // The rest of the hidden bar
        loadingBg.setSize(450, 50);
        loadingBg.setX(loadingBarHidden.getX());
        loadingBg.setY(loadingBarHidden.getY() - 2);


    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (game.manager.update())
        { // Load some, will return true if done
            // loading

            float progress = game.manager.getProgress();
            if (percent >= .9f)
            {

                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        }
        // Interpolate the percentage to make it more smooth
        percent = Interpolation.linear.apply(percent, game.manager.getProgress(), 0.03f);

        // Update positions (and size) to match the percentage
        loadingBarHidden.setX(startX + endX * percent);
        loadingBg.setX(loadingBarHidden.getX() + 5);
        loadingBg.setWidth(450 - 450 * percent);
        loadingBg.invalidate();

        // Show the loading screen
        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        // Dispose the loading assets as we no longer need them
        //   game.manager.unload("ui/newUI.pack");
    }
}