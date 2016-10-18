package com.bignerdranch.android.photogallery;

import android.provider.ContactsContract;
import android.support.v4.app.Fragment;


public class PhotoGalleryActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return PhotoGalleryFragment.newInstance();
    }
}
