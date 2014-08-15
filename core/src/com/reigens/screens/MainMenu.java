package com.reigens.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.reigens.MasterWarrior;
import com.reigens.tween.ActorAssessor;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by Rich on 8/8/2014.
 */
public class MainMenu implements Screen {
    private Stage stage;
    private Skin skin;
    private Table table;
    private TweenManager tweenManager;
    private Image screenBg;


    @Override
    public void render(float delta) {
        delta = MathUtils.clamp(delta, 0, 1 / 30f);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        tweenManager.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        // Make the background fill the screen
        screenBg.setSize(width, height);
        table.invalidateHierarchy();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        TextureAtlas uiPack = MasterWarrior.manager.get("ui/newUI.pack", TextureAtlas.class);
        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), uiPack);
        screenBg = new Image(uiPack.findRegion("blank_window"));
        stage.addActor(screenBg);


        table = new Table(skin);
        table.setFillParent(true);

        Label heading = new Label(MasterWarrior.TITLE, skin, "bigWhite");
        heading.setFontScale(1.5f);

        //Play Button
        TextButton buttonPlay = new TextButton("PLAY", skin, "default");

        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f), run(new Runnable() {
                    @Override
                    public void run() {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelsScreen());
                    }
                })));
            }
        });
        buttonPlay.pad(15);

        //Settings Button
        TextButton buttonSettings = new TextButton("Settings", skin, "default");
        buttonSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f), run(new Runnable() {
                    @Override
                    public void run() {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new SettingsScreen());
                    }
                })));
            }
        });
        buttonSettings.pad(15);

        //Exit Button
        TextButton buttonExit = new TextButton("Exit", skin, "default");
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        buttonExit.pad(15);

        //Setup Table
        table.add(heading).spaceBottom(100).row();
        table.add(buttonPlay).spaceBottom(15).row();
        table.add(buttonSettings).spaceBottom(15).row();
        table.add(buttonExit);

        stage.addActor(table);

        //Creating animations for menu
        tweenManager = new TweenManager();
        Tween.registerAccessor(Actor.class, new ActorAssessor());

        //Heading Color Animation
        Timeline.createSequence().beginSequence()
                .push(Tween.to(heading, ActorAssessor.RGB, .5f).target(0, 0, 1))
                .push(Tween.to(heading, ActorAssessor.RGB, .5f).target(0, 1, 0))
                .push(Tween.to(heading, ActorAssessor.RGB, .5f).target(1, 0, 0))
                .push(Tween.to(heading, ActorAssessor.RGB, .5f).target(1, 1, 0))
                .push(Tween.to(heading, ActorAssessor.RGB, .5f).target(0, 1, 1))
                .push(Tween.to(heading, ActorAssessor.RGB, .5f).target(1, 0, 1))
                .push(Tween.to(heading, ActorAssessor.RGB, .5f).target(1, 1, 1))
                .end().repeat(Tween.INFINITY, 0).start(tweenManager);

        //Heading and Buttons Fade in
        Timeline.createSequence().beginSequence()
                .push(Tween.set(buttonPlay, ActorAssessor.ALPHA).target(0))
                .push(Tween.set(buttonSettings, ActorAssessor.ALPHA).target(0))
                .push(Tween.set(buttonExit, ActorAssessor.ALPHA).target(0))
                .push(Tween.from(heading, ActorAssessor.ALPHA, .5f).target(0))
                .push(Tween.to(buttonPlay, ActorAssessor.ALPHA, .5f).target(1))
                .push(Tween.to(buttonSettings, ActorAssessor.ALPHA, .5f).target(1))
                .push(Tween.to(buttonExit, ActorAssessor.ALPHA, .5f).target(1))
                .end().start(tweenManager);

        //Table fade in
        Tween.from(table, ActorAssessor.ALPHA, .5f).target(0).start(tweenManager);
        Tween.from(table, ActorAssessor.Y, .5f).target(Gdx.graphics.getHeight() / 8).start(tweenManager);

        tweenManager.update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void hide() {
        dispose();
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
