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
import com.reigens.MasterWarrior;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by Rich on 8/10/2014.
 */
public class GoldScreen implements Screen {

    long lastGold = TimeUtils.millis();
    private Stage stage;
    private Table table;
    private Skin skin;
    private Image screenBg;
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


        //Upgrade 1 Button
        TextButton upgradegold = new TextButton("Upgrade", skin);
        upgradegold.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Dialog("Upgrade?", skin) {
                    {
                        ;
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
        table.add(new Label("Gold", skin, "big")).padTop(100).colspan(3).expandX().spaceBottom(50).row();
        lablegold = new Label(String.format("Earning Gold: %s", goldamt), skin);
        table.add(lablegold).padLeft(125).uniformX().expandY().left().row();
        table.add(new Label("Gold Level: " + (String.valueOf(goldLvl)), skin, "default"));
        table.add(upgradegold).uniformX();
        table.add(back).padBottom(100).padRight(125).uniformX().bottom().right();

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


    }
}
