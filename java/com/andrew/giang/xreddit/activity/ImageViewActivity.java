package com.andrew.giang.xreddit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.andrew.giang.xreddit.R;
import com.andrew.giang.xreddit.fragments.ImageFragment;

public class ImageViewActivity extends ActionBarActivity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);
        Intent intent = getIntent();
        if (intent != null) {
            String image_url = intent.getStringExtra("image_url");
            if (image_url != null) {
                setContentView(R.layout.activity_image_view);


                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, ImageFragment.getInstance(image_url)).commit();
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}