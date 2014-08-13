package com.reigens.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.reigens.MasterWarrior;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by Rich on 8/9/2014.
 */
public class SettingsScreen implements Screen {
    private Stage stage;
    private Table table;
    private Skin skin;
    private Image screenBg;

    public static FileHandle levelDirectory() {
        String prefsDir = Gdx.app.getPreferences(MasterWarrior.TITLE).getString("levelDirectory").trim();
        if (prefsDir != null && !prefsDir.equals(""))
            return Gdx.files.absolute(prefsDir);
        else
            return Gdx.files.absolute(Gdx.files.external(MasterWarrior.TITLE + "/levels").path());
    }

    public static boolean vSync() {
        return Gdx.app.getPreferences(MasterWarrior.TITLE).getBoolean("vsync");
    }

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
        //table.debug();

        //vSync CheckBox
        final CheckBox vSyncCheckBox = new CheckBox("vSync", skin);
        vSyncCheckBox.setChecked(vSync());

        //Level Directory input field
        final TextField levelDirectoryInput = new TextField(levelDirectory().path(), skin);
        levelDirectoryInput.setMessageText("level directory");

        //Back Button
        final TextButton back = new TextButton("BACK", skin);
        back.pad(10);

        ClickListener buttonHandler = new ClickListener() {


            @Override
            public void clicked(InputEvent event, float x, float y) {
                // event.getListenerActor() returns the source of the event, e.g. a button that was clicked
                if (event.getListenerActor() == vSyncCheckBox)
                {
                    // save vSync
                    Gdx.app.getPreferences(MasterWarrior.TITLE).putBoolean("vsync", vSyncCheckBox.isChecked());

                    // set vSync
                    Gdx.graphics.setVSync(vSync());

                    Gdx.app.log(MasterWarrior.TITLE, "vSync " + (vSync() ? "enabled" : "disabled"));
                }
                else if (event.getListenerActor() == back)
                {
                    // save level directory
                    String actualLevelDirectory = levelDirectoryInput.getText().trim().equals("") ? Gdx.files.getExternalStoragePath() + MasterWarrior.TITLE + "/levels" : levelDirectoryInput.getText().trim(); // shortened form of an if-statement: [boolean] ? [if true] : [else] // String#trim() removes spaces on both sides of the string
                    Gdx.app.getPreferences(MasterWarrior.TITLE).putString("leveldirectory", actualLevelDirectory);

                    // save the settings to preferences file (Preferences#flush() writes the preferences in memory to the file)
                    Gdx.app.getPreferences(MasterWarrior.TITLE).flush();

                    Gdx.app.log(MasterWarrior.TITLE, "settings saved");

                    stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {

                        @Override
                        public void run() {
                            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                        }
                    })));
                }
            }
        };


        vSyncCheckBox.addListener(buttonHandler);

        back.addListener(buttonHandler);

        // Setup Table
        table.add(new Label("SETTINGS", skin, "big")).padTop(100).spaceBottom(50).colspan(3).expandX().row();
        table.add();
        table.add("level directory");
        table.add().row();
        table.add(vSyncCheckBox).top().expandY();
        table.add(levelDirectoryInput).top().fillX();
        table.add(back).padBottom(100).padRight(125).bottom().right();

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


