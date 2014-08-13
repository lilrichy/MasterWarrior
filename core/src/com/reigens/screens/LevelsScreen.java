package com.reigens.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.reigens.MasterWarrior;
import com.reigens.screens.Levels.LevelOne;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class LevelsScreen implements Screen {
    private Stage stage;
    private Table table;
    private Skin skin;
    private Image screenBg;

    @Override
    public void render(float delta) {
        delta = MathUtils.clamp(delta, 0, 1 / 30f);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
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
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        TextureAtlas uiPack = MasterWarrior.manager.get("ui/newUI.pack", TextureAtlas.class);
        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), uiPack);
        screenBg = new Image(uiPack.findRegion("blank_window"));
        stage.addActor(screenBg);


        table = new Table(skin);
        table.setFillParent(true);

        final List list = new List(skin);
        list.setItems(new String[]{"Play", "Menu", "Level 1"});
        ScrollPane scrollPane = new ScrollPane(list, skin);

        //Play button
        TextButton play = new TextButton("PLAY", skin);
        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {
                    @Override
                    public void run() {
                        switch (list.getSelectedIndex())
                        {
                            case 0:
                                ((Game) Gdx.app.getApplicationListener()).setScreen(new com.reigens.screens.play());
                                break;
                            case 1:
                                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                                break;
                            case 2:
                                ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelOne());
                                break;


                        }
                    }
                })));
            }
        });
        play.pad(10);

        //Back Button
        TextButton back = new TextButton("BACK", skin);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {

                    @Override
                    public void run() {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                    }
                })));
            }
        });
        back.pad(10);

        //Set up Table

        table.add(new Label("SELECT LEVEL", skin, "big")).padTop(100).colspan(3).expandX().spaceBottom(50).row();
        table.add(scrollPane).uniformX().expandY().padLeft(125).left();
        table.add(play).uniformX();
        table.add(back).uniformX().padBottom(100).padRight(125).bottom().right();

        stage.addActor(table);

        stage.addAction(sequence(moveTo(0, stage.getHeight()), moveTo(0, 0, .5f))); // coming in from top animation
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