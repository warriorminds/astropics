package com.warriorminds.astropics.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.warriorminds.astropics.R;
import com.warriorminds.astropics.utils.Constants;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        String imageUrl = getIntent().getExtras().getString(Constants.IMAGE_URL_EXTRA);

        Glide.with(this)
                .load(imageUrl)
                .fitCenter()
                        //.placeholder(R.drawable.loading_spinner)
                .crossFade()
                .into(imageView);
    }
}
