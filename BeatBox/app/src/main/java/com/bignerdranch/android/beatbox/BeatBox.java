package com.bignerdranch.android.beatbox;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.LayoutInflater;

import java.io.IOException;

/**
 * Created by YKC on 2016. 10. 3..
 */
public class BeatBox {
    private static final String TAG = "BeatBox";

    private static final String SOUNDS_FOLDER = "sample_sounds";

    private AssetManager mAssets; // AssetManager 클래스를 사용하여 에셋에 엑세스

    public BeatBox(Context context) {
        mAssets = context.getAssets();
        loadsounds();
    }

    private void loadsounds() {
        String[] soundNames;
        try {
            soundNames = mAssets.list(SOUNDS_FOLDER);
            Log.i(TAG, "Found" + soundNames.length + "sounds");
        } catch (IOException ioe) {
            Log.i(TAG, "Could not list assets", ioe);
            return;
        }
    }

}
