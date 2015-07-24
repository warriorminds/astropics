package com.warriorminds.astropics.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.warriorminds.astropics.R;
import com.warriorminds.astropics.retrofit.Image;
import com.warriorminds.astropics.retrofit.Location;
import com.warriorminds.astropics.tasks.GetLocationTask;
import com.warriorminds.astropics.utils.Constants;
import com.warriorminds.astropics.utils.Utils;

public class PictureDetailActivity extends AppCompatActivity implements GetLocationTask.IGetImageLocation{

    private ImageView mImageBig;
    private CardView cvDescription, cvTechInfo, cvSubjects;
    private TextView tvDescription, tvImagingCameras, tvImagingTelescopes, tvLocation, tvContains;
    private FloatingActionButton fab;
    private Image image;
    private GetLocationTask locationTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_detail);

        setupToolbar();
        retrieveImage();
        setupOtherViewElements();
        init();
        getLocation();
    }

    @Override
    protected void onDestroy() {
        if(locationTask != null)
            locationTask.cancel(true);
        super.onDestroy();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cvDescription = (CardView) findViewById(R.id.cvDescription);
    }

    private void setupOtherViewElements() {
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(image.getTitle());

        mImageBig = (ImageView) findViewById(R.id.ivBigImage);

        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvImagingTelescopes = (TextView) findViewById(R.id.tvImagingTelescopesValue);
        tvImagingCameras = (TextView) findViewById(R.id.tvImagingCamerasValue);
        tvContains = (TextView) findViewById(R.id.tvContains);
        tvLocation = (TextView) findViewById(R.id.tvLocation);

        cvTechInfo = (CardView) findViewById(R.id.cvTechInfo);
        cvSubjects = (CardView) findViewById(R.id.cvContains);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PictureDetailActivity.this, ImageActivity.class);
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

    private void retrieveImage() {
        image = (Image) getIntent().getSerializableExtra(Constants.IMAGE_EXTRA);
    }

    private void init() {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getLocation(){
        locationTask = new GetLocationTask(this);
        locationTask.execute(image.getLocations());
    }

    public void displayLocation(Location location) {
        if (location != null) {
            tvLocation.setText(location.getName() + " - " + location.getCity() + ", " + location.getCountry() );
        }else{
            tvLocation.setText(getString(R.string.not_available));
        }
    }
}
