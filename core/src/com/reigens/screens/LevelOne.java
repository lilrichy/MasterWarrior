package com.reigens.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.reigens.screens.helper.OrthoCamController;


/**
 * Created by Rich on 8/12/2014.
 */
public class LevelOne implements Screen, InputProcessor {

    private static final int TARGET_WIDTH = 1028;
    private static final float UNIT_TO_PIXEL = TARGET_WIDTH * 0.15f;
    final Matrix4 matrix = new Matrix4();
    final Plane xzPlane = new Plane(new Vector3(0, 1, 0), 0);
    final Vector3 intersection = new Vector3();
    OrthoCamController orthoCamController;
    GestureDetector gesture;
    Sprite lastSelectedTile = null;
    private TiledMap map;
    private IsometricTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.setView(camera);
        mapRenderer.render();


        checkTileTouched();
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        // setupCamera(width, height);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);

        map = new TmxMapLoader().load("maps/testMap.tmx");
        mapRenderer = new IsometricTiledMapRenderer(map);

        camera = new OrthographicCamera(Gdx.graphics.getWidth() * 4, Gdx.graphics.getHeight() * 4);
        TiledMapTileLayer layer0 = (TiledMapTileLayer) map.getLayers().get(1);
        Vector3 center = new Vector3(layer0.getWidth() * layer0.getTileWidth() / 2, layer0.getHeight() * layer0.getTileHeight() / 10, 20);
        camera.position.set(center);
        camera.near = 1;
        camera.far = 1000;


        orthoCamController = new OrthoCamController(camera);


        System.out.println("Show Method");


    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        return orthoCamController.touchDragged(x, y, pointer);
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        return orthoCamController.touchUp(x, y, pointer, button);
    }

    private void checkTileTouched() {
        if (Gdx.input.justTouched())
        {
            Ray pickRay = camera.getPickRay(Gdx.input.getX(), Gdx.input.getY());
            Intersector.intersectRayPlane(pickRay, xzPlane, intersection);
            //System.out.println(intersection);
            int x = (int) intersection.x;
            int z = (int) intersection.z;
            if (x >= 0 && x < 10 && z >= 0 && z < 10)
            {
                if (lastSelectedTile != null) lastSelectedTile.setColor(1, 1, 1, 1);

            }
        }
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
        map.dispose();
        mapRenderer.dispose();
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public boolean keyDown(int keycode) {
        switch (keycode)
        {
            case Input.Keys.W:
                System.out.println("Up");
                camera.translate(0, 300, 0);
                break;

            case Input.Keys.A:
                camera.translate(-300, 0, 0);
                System.out.println("Left");

                break;
            case Input.Keys.S:
                camera.translate(0, -300, 0);
                System.out.println("Down");
                break;

            case Input.Keys.D:
                camera.translate(300, 0, 0);
                System.out.println("Right");
                break;

            case Input.Keys.PLUS:
                camera.zoom += 0.5;
                break;

            case Input.Keys.MINUS:
                camera.zoom -= 0.5;


        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

}
