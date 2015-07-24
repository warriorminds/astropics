package com.warriorminds.astropics.activities;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.warriorminds.astropics.R;
import com.warriorminds.astropics.adapters.ImageAdapter;
import com.warriorminds.astropics.retrofit.Image;
import com.warriorminds.astropics.tasks.SearchTask;
import com.warriorminds.astropics.utils.Search;
import com.warriorminds.astropics.utils.Utils;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SearchTask.ISearchResults, SearchView.OnQueryTextListener {

    private SearchView mSearchView;
    private Toolbar toolbar;
    private ImageAdapter imageAdapter;
    private RecyclerView mRecyclerView;

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int current_page = 1;
    private String searchTerm;
    private ProgressBar progressBar;
    private TextView tvError;

    private SearchTask searchTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setupToolbar();
        setupRecyclerView();
        setupViewElements();
    }

    @Override
    protected void onDestroy() {
        if(searchTask != null)
            searchTask.cancel(true);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        setupSearchView(searchItem);

        return true;
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

    /* Setup view elements */
    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.search));
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

                    loadNextImages(20 * current_page, 20 * current_page, searchTerm);

                    // Do something
                    current_page++;
                    loading = true;
                }
            }
        });
    }

    private void setupViewElements(){
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvError = (TextView) findViewById(R.id.no_results);
    }

    private void setupSearchView(MenuItem searchItem) {

        if(searchTerm != null && !searchTerm.equals("")){
            mSearchView.onActionViewCollapsed();
            hideKeyboard();
            getSupportActionBar().setTitle(searchTerm);
        }else{
            mSearchView.setIconified(false);
        }

        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    showInputMethod(view.findFocus());
                }
            }
        });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        if(query != null && !query.equals("")) {
            tvError.setVisibility(View.GONE);
            searchTerm = query;
            mRecyclerView.setAdapter(null);
            imageAdapter = null;
            search(query);
            searchCompleted();
            return true;
        }else
            Snackbar.make(mRecyclerView, getString(R.string.search_string_error), Snackbar.LENGTH_SHORT).show();

        return false;
    }

    private void search(String query) {
        searchTask = new SearchTask(this);
        Search search = new Search();
        search.setSearchTerm(query);
        searchTask.execute(search);
    }

    public void loadNextImages(int limit, int offset, String searchTerm) {
        if (searchTask == null) {
            searchTask = new SearchTask(this);
            Search search = new Search();
            search.setSearchTerm(searchTerm);
            search.setLimit(limit);
            search.setOffset(offset);
            searchTask.execute(search);
        }
    }

    public void hideKeyboard(){
        Utils.hideKeyboard(this,
                mSearchView.getWindowToken());
    }

    private void searchCompleted(){
        mSearchView.onActionViewCollapsed();
        hideKeyboard();
        getSupportActionBar().setTitle(searchTerm);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void showInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, 0);
        }
    }

    public void displayResults(ArrayList<Image> imageList) {
        progressBar.setVisibility(View.GONE);
        searchTask = null;
        if (imageList != null && imageList.size() > 0) {
            if (imageAdapter == null) {
                imageAdapter = new ImageAdapter(imageList, this);
                mRecyclerView.setAdapter(imageAdapter);
            } else {
                imageAdapter.getImageArrayList().addAll(imageList);
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }
}
