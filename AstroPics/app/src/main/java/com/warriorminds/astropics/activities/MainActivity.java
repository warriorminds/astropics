package com.warriorminds.astropics.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.warriorminds.astropics.R;
import com.warriorminds.astropics.adapters.ImageAdapter;
import com.warriorminds.astropics.retrofit.Image;
import com.warriorminds.astropics.retrofit.ImageList;

import java.util.ArrayList;
import java.util.Random;

import com.warriorminds.astropics.tasks.GetImagesTask;
import com.warriorminds.astropics.tasks.GetImagesTask.IGetImageResults;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IGetImageResults {

    private final long DRAWER_CLOSE_DELAY_MS = 300;

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private RecyclerView mRecyclerView;
    private Toolbar toolbar;
    private final Handler mDrawerActionHandler = new Handler();
    private FloatingActionButton mFab;
    private ProgressBar progressBar;
    private boolean isRefreshAvailable = false;

    private ImageAdapter imageAdapter;
    private ImageList imageListResult;

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int current_page = 1;

    private GetImagesTask getImagesTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupNavigationDrawer();
        setupRecyclerView();
        setupOtherViewElements();
        downloadImages();


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
                downloadImages();
                isRefreshAvailable = false;
                invalidateOptionsMenu();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /* Setup view elements */
    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

    }

    private void setupNavigationDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mNavigationView.setNavigationItemSelectedListener(this);
        mDrawerToggle.syncState();
    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rvImages);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.column_number)));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = recyclerView.getLayoutManager().getItemCount();
                firstVisibleItem = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached

                    loadNextImages(20 * current_page, 20 * current_page);

                    // Do something
                    current_page++;
                    loading = true;
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupOtherViewElements() {

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }


    /* Navigation methods */
    private void navigate(final int menuId) {
        switch (menuId) {
            case R.id.nav_image_of_the_day:
                //startActivity(new Intent(MainActivity.this, ImageOfTheDayActivity.class));
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(final MenuItem menuItem) {
        mDrawerLayout.closeDrawers();

        mDrawerLayout.closeDrawer(GravityCompat.START);
        mDrawerActionHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(menuItem.getItemId());
            }
        }, DRAWER_CLOSE_DELAY_MS);
        return false;
    }

    public void downloadImages(){
        getImagesTask = new GetImagesTask(this);
        getImagesTask.execute();
    }

    public void loadNextImages(int limit, int offset) {
        if(getImagesTask != null)
            getImagesTask = null;
        getImagesTask = new GetImagesTask(this);
        getImagesTask.execute(limit, offset);
    }

    public void displayResults(ArrayList<Image> imageList) {

        progressBar.setVisibility(View.GONE);
        if (imageList != null && imageList.size() > 0) {
            setNavigationHeaderImage(imageList);
            if (imageAdapter == null) {
                imageAdapter = new ImageAdapter(imageList, this);
                mRecyclerView.setAdapter(imageAdapter);
            } else {
                imageAdapter.getImageArrayList().addAll(imageList);
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
        } else {
            Toast.makeText(this, getString(R.string.no_results), Toast.LENGTH_SHORT).show();
            isRefreshAvailable = true;
            invalidateOptionsMenu();
        }
    }

    private void setNavigationHeaderImage(ArrayList<Image> imageList){
        ImageView imageView = (ImageView) findViewById(R.id.imgNavHeader);
        Image image = imageList.get(new Random().nextInt(imageList.size() - 1));
        Glide.with(this)
                .load(image.getUrlRegular())
                .centerCrop()
                        //.placeholder(R.drawable.loading_spinner)
                .crossFade()
                .into(imageView);
    }
}
