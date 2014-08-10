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

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class Levels implements Screen {
    private Stage stage;
    private Table table;
    private Skin skin;

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
        table.invalidateHierarchy();
    }

    @Override
    public void show() {
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), new TextureAtlas("ui/atlas.pack"));

        table = new Table(skin);
        table.setFillParent(true);

        final List list = new List(skin);
        list.setItems(new String[]{"Play", "Menu"});
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
        table.add(new Label("SELECT LEVEL", skin, "big")).colspan(3).expandX().spaceBottom(50).row();
        table.add(scrollPane).uniformX().expandY().left();
        table.add(play).uniformX();
        table.add(back).uniformX().bottom().right();

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
        stage.dispose();
        skin.dispose();
    }

}