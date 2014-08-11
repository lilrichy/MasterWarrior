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
import com.badlogic.gdx.utils.TimeUtils;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by Rich on 8/10/2014.
 */
public class GoldScreen implements Screen {

    long lastGold = TimeUtils.millis();
    private Stage stage;
    private Table table;
    private Skin skin;
    private long goldamt;
    private Label lablegold;
    private int goldLvl;
    private boolean isUpgradeing;






    @Override
    public void render(float delta) {
        delta = MathUtils.clamp(delta, 0, 1 / 30f);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (TimeUtils.millis() - lastGold > 10000)
        {
            updateGold();
        }
        // System.out.print(TimeUtils.millis());

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


        //Upgrade 1 Button
        TextButton upgradegold = new TextButton("Upgrade", skin);
        upgradegold.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Upgrade?", skin) {
                    {


                        text("\n\n" +
                                "Are you sure you want to spend" +

                                "\n" +
                                "this much gold?" +
                                "\n\n ");
                        button("Purchase!", true);
                        button("Cancel", false);
                    }

                    @Override
                    protected void result(Object object) {
                        if (object.equals(true))
                        {
                            preformUpgrade();
                            System.out.println("true");
                        }
                        else if (object.equals(false))
                        {
                            System.out.println("false");
                        }
                    }
                }.show(stage).setFillParent(false);
            }


        });

        upgradegold.pad(10);

        //Back Button
        TextButton back = new TextButton("BACK", skin);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {

                    @Override
                    public void run() {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new play());
                    }
                })));
            }
        });
        back.pad(10);

        //Set up Table
        table.add(new Label("Gold", skin, "big")).colspan(3).expandX().spaceBottom(50).row();
        lablegold = new Label(String.format("Earning Gold: %s", goldamt), skin);
        table.add(lablegold).uniformX().expandY().left();
        table.add(upgradegold).uniformX();
        table.add(back).uniformX().bottom().right();

        stage.addActor(table);

        stage.addAction(sequence(moveTo(0, stage.getHeight()), moveTo(0, 0, .5f))); // coming in from top animation
    }

    //my methods
    private void updateGold() {

        lablegold.setText(String.format("Earning Gold: %s", goldamt));
        goldamt++;
        lastGold = TimeUtils.millis();
    }

    private void preformUpgrade() {
        isUpgradeing = true;
    }


    // --------------------
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
