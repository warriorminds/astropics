package com.warriorminds.astropics.tasks;

import android.os.AsyncTask;

import com.warriorminds.astropics.retrofit.AstrobinWebServiceProxy;
import com.warriorminds.astropics.retrofit.Image;
import com.warriorminds.astropics.retrofit.ImageList;
import com.warriorminds.astropics.retrofit.RetrofitErrorHandler;
import com.warriorminds.astropics.utils.Search;

import java.util.ArrayList;

import retrofit.RestAdapter;

/**
 * Created by rodrigo.guerrero on 7/24/2015.
 */
public class SearchTask extends AsyncTask<Search, Void, ImageList> {

    private ISearchResults searchResults;
    private AstrobinWebServiceProxy astrobinWebServiceProxy;

    public interface ISearchResults {
        void displayResults(ArrayList<Image> imageList);
    }

    public SearchTask(ISearchResults searchResults){
        this.searchResults = searchResults;
    }

    @Override
    protected void onPreExecute() {
        astrobinWebServiceProxy = new RestAdapter.Builder()
                .setEndpoint(AstrobinWebServiceProxy.ENDPOINT)
                .setErrorHandler(new RetrofitErrorHandler())
                .build()
                .create(AstrobinWebServiceProxy.class);

    }

    @Override
    protected ImageList doInBackground(Search... params) {
        ImageList results = null;
        try {

            if (params != null && params.length > 0) {
                Search search = params[0];
                if (search.getLimit() > 0 && search.getOffset() > 0) {
                    results = astrobinWebServiceProxy.SearchBySubject((params[0]).getSearchTerm(),
                            AstrobinWebServiceProxy.API_KEY_VALUE, AstrobinWebServiceProxy.API_SECRET_VALUE,
                            AstrobinWebServiceProxy.FORMAT_VALUE, search.getOffset(), search.getLimit());
                    if(results == null || results.getImagesList().size() == 0)
                        results = astrobinWebServiceProxy.SearchByTitle((params[0]).getSearchTerm(),
                                AstrobinWebServiceProxy.API_KEY_VALUE, AstrobinWebServiceProxy.API_SECRET_VALUE,
                                AstrobinWebServiceProxy.FORMAT_VALUE, search.getOffset(), search.getLimit());
                } else {
                    results = astrobinWebServiceProxy.SearchBySubject((params[0]).getSearchTerm(),
                            AstrobinWebServiceProxy.API_KEY_VALUE, AstrobinWebServiceProxy.API_SECRET_VALUE,
                            AstrobinWebServiceProxy.FORMAT_VALUE);
                    if(results == null || results.getImagesList().size() == 0)
                        results = astrobinWebServiceProxy.SearchByTitle((params[0]).getSearchTerm(),
                                AstrobinWebServiceProxy.API_KEY_VALUE, AstrobinWebServiceProxy.API_SECRET_VALUE,
                                AstrobinWebServiceProxy.FORMAT_VALUE);
                }
            } else {
                return null;
            }
        } catch (Exception ex) {

        }

        return results;
    }

    @Override
    protected void onPostExecute(ImageList imageList) {
        searchResults.displayResults(imageList.getImagesList());
    }
}
