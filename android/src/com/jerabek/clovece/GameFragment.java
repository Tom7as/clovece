package com.jerabek.clovece;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;

/**
 * Created by Tomas on 19.02.2017.
 */

public class GameFragment extends AndroidFragmentApplication {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // return the GLSurfaceView on which libgdx is drawing game stuff
        return initializeForView(new CloveceNezlobSe());
    }
}