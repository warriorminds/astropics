package com.warriorminds.astropics.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.warriorminds.astropics.R;
import com.warriorminds.astropics.retrofit.Image;
import com.warriorminds.astropics.retrofit.Location;
import com.warriorminds.astropics.tasks.GetImageOfTheDayTask;
import com.warriorminds.astropics.tasks.GetImagesTask;
import com.warriorminds.astropics.utils.Constants;
import com.warriorminds.astropics.utils.ImageOfTheDayLocation;
import com.warriorminds.astropics.utils.Utils;

public class ImageOfTheDayActivity extends AppCompatActivity implements GetImageOfTheDayTask.IImageOfTheDayResult {

    private ImageView mImageBig;
    private NestedScrollView detailsLayout;
    private TextView tvDescription, tvImagingCameras, tvImagingTelescopes, tvLocation, tvContains;
    private FloatingActionButton fab;
    private ProgressBar progressBar;
    private boolean isRefreshAvailable = false;
    private GetImageOfTheDayTask getImageOfTheDayTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_detail);
        setupToolbar();
        setupLayout();
        getImageOfTheDay();
    }

    @Override
    protected void onDestroy() {
        if(getImageOfTheDayTask != null)
            getImageOfTheDayTask.cancel(true);
        super.onDestroy();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }

    private void setupLayout(){
        detailsLayout = (NestedScrollView) findViewById(R.id.details_layout);
        detailsLayout.setVisibility(View.GONE);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void setupImage(final Image image) {
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(image.getTitle());

        mImageBig = (ImageView) findViewById(R.id.ivBigImage);

        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvImagingTelescopes = (TextView) findViewById(R.id.tvImagingTelescopesValue);
        tvImagingCameras = (TextView) findViewById(R.id.tvImagingCamerasValue);
        tvContains = (TextView) findViewById(R.id.tvContains);
        tvLocation = (TextView) findViewById(R.id.tvLocation);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageOfTheDayActivity.this, ImageActivity.class);
                intent.putExtra(Constants.IMAGE_URL_EXTRA, image.getUrl());
                startActivity(intent);
            }
        });

        Glide.with(this)
                .load(image.getUrlRegular())
                .centerCrop()
                        //.placeholder(R.drawable.loading_spinner)
                .crossFade()
                .into(mImageBig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        MenuItem refresh = menu.findItem(R.id.action_refresh);
        refresh.setVisible(isRefreshAvailable);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.action_refresh:
                getImageOfTheDay();
                progressBar.setVisibility(View.VISIBLE);
                isRefreshAvailable = false;
                invalidateOptionsMenu();
                break;
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init(Image image) {
        if (image.getDescription() != null && !image.getDescription().equals("")) {
            tvDescription.setText(image.getDescription());
        } else {

            tvDescription.setText(getString(R.string.not_available));
        }

        Utils.setArrayInformation(tvImagingTelescopes, image.getTelescopes(), false);
        Utils.setArrayInformation(tvImagingCameras, image.getCameras(), false);

        if ((image.getTelescopes() == null) || image.getTelescopes().length == 0){
            tvImagingTelescopes.setText(getString(R.string.not_available));

        }
        if((image.getCameras() == null || image.getCameras().length == 0)) {
            tvImagingCameras.setText(getString(R.string.not_available));
        }

        Utils.setArrayInformation(tvContains, image.getSubjects(), true);

        if(image.getSubjects() == null || image.getSubjects().length == 0){
            tvContains.setText(getString(R.string.not_available));
        }
    }

    private void getImageOfTheDay(){
        getImageOfTheDayTask = new GetImageOfTheDayTask(this);
        getImageOfTheDayTask.execute();

    }

    public void displayImageOfTheDay(ImageOfTheDayLocation image) {

        progressBar.setVisibility(View.GONE);
        getImageOfTheDayTask = null;
        if(image != null){
            detailsLayout.setVisibility(View.VISIBLE);
            isRefreshAvailable = false;
            setupImage(image.getImage());
            init(image.getImage());
            setupLocation(image.getLocation());
        }else{
            isRefreshAvailable = true;
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            invalidateOptionsMenu();
        }
    }

    private void setupLocation(Location location) {
        if (location != null) {
            tvLocation.setText(location.getName() + " - " + location.getCity() + ", " + location.getCountry() );
        }else{
            tvLocation.setText(getString(R.string.not_available));
        }
    }
}
