package com.reigens.screens.helper;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;

/**
 * Created by Rich on 8/15/2014.
 */
public class MyGestureListener extends GestureDetector.GestureAdapter {

    final OrthographicCamera camera;

    public MyGestureListener(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        float ratio = initialDistance / distance;
        camera.zoom = .5f * ratio;
        System.out.println(initialDistance + distance + "Zoom" + ratio);
        return true;
    }


}