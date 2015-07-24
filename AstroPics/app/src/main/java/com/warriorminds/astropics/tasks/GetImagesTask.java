package com.warriorminds.astropics.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.warriorminds.astropics.retrofit.AstrobinWebServiceProxy;
import com.warriorminds.astropics.retrofit.Image;
import com.warriorminds.astropics.retrofit.ImageList;
import com.warriorminds.astropics.retrofit.RetrofitErrorHandler;

import java.util.ArrayList;

import retrofit.RestAdapter;

/**
 * Created by rodrigo.guerrero on 7/23/2015.
 */
public class GetImagesTask extends AsyncTask<Integer, Void, ImageList> {

    private AstrobinWebServiceProxy astrobinWebServiceProxy;
    private IGetImageResults processResults;

    public interface IGetImageResults {
        void displayResults(ArrayList<Image> imageList);
    }

    public GetImagesTask(IGetImageResults processResults){
        this.processResults = processResults;
    }

    @Override
    protected void onPreExecute() {
        astrobinWebServiceProxy = new RestAdapter.Builder()
                .setEndpoint(AstrobinWebServiceProxy.ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setErrorHandler(new RetrofitErrorHandler())
                .build()
                .create(AstrobinWebServiceProxy.class);
    }

    @Override
    public ImageList doInBackground(Integer... params) {
        int limit = 0, offset = 0;

        if (params != null && params.length >= 2) {
            limit = params[0];
            offset = (params.length > 1) ? params[1] : limit;
        }

        ImageList result = null;

        try {
            if (limit != 0 && offset != 0) {
                result = astrobinWebServiceProxy.GetAllImages(AstrobinWebServiceProxy.API_KEY_VALUE,
                        AstrobinWebServiceProxy.API_SECRET_VALUE, AstrobinWebServiceProxy.FORMAT_VALUE, offset, limit);
            } else {
                result = astrobinWebServiceProxy.GetAllImages(AstrobinWebServiceProxy.API_KEY_VALUE,
                        AstrobinWebServiceProxy.API_SECRET_VALUE, AstrobinWebServiceProxy.FORMAT_VALUE);
            }
        } catch (Exception ex) {
            Log.d("AstroPics", ex.toString());
        }

        return result;
    }

    @Override
    public void onPostExecute(ImageList imageList) {
        if(imageList != null)
            processResults.displayResults((imageList.getImagesList() != null) ? imageList.getImagesList() : null);
        else
            processResults.displayResults(null);
    }
}
