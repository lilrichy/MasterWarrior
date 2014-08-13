package com.reigens.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.reigens.MasterWarrior;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by Rich on 8/10/2014.
 */
public class play implements Screen {
    private Stage stage;
    private Table table;
    private Skin skin;
    private Image screenBg;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        debugRenderer.render(world, camera.combined);

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
        //Gold button
        TextButton gold = new TextButton("Gold", skin);
        gold.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {
                    @Override
                    public void run() {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new GoldScreen());
                    }
                })));
            }
        });
        gold.pad(10);
        //Battle button
        TextButton battle = new TextButton("Battle", skin);
        battle.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {
                    @Override
                    public void run() {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new BattleScreen());
                    }
                })));
            }
        });
        battle.pad(10);
        //Upgrades button
        TextButton upgrades = new TextButton("Upgrade Shop", skin);
        upgrades.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {
                    @Override
                    public void run() {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new upgradeScreen());
                    }
                })));
            }
        });
        upgrades.pad(10);


        //Set up Table
        table.add(new Label("Where to go?", skin, "big")).padTop(100).colspan(5).expandX().spaceBottom(50).row();
        table.add().row().expandY();
        // Add Buttons to table
        table.add();
        table.add(gold).uniformX();
        table.add(battle);
        table.add(upgrades);
        table.add(back).padBottom(100).padRight(125).uniformX().bottom().right();

        stage.addActor(table);

        stage.addAction(sequence(moveTo(0, stage.getHeight()), moveTo(0, 0, .5f))); // coming in from top animation

        world = new World(new Vector2(0, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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

        world.dispose();
        debugRenderer.dispose();
    }
}
