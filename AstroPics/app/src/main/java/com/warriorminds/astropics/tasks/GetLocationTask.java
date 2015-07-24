package com.warriorminds.astropics.tasks;

import android.os.AsyncTask;

import com.warriorminds.astropics.retrofit.AstrobinWebServiceProxy;
import com.warriorminds.astropics.retrofit.Image;
import com.warriorminds.astropics.retrofit.Location;
import com.warriorminds.astropics.retrofit.RetrofitErrorHandler;
import com.warriorminds.astropics.utils.Utils;

import java.util.ArrayList;

import retrofit.RestAdapter;

/**
 * Created by rodrigo.guerrero on 7/24/2015.
 */
public class GetLocationTask extends AsyncTask<String, Void, Location> {

    private AstrobinWebServiceProxy astrobinWebServiceProxy;
    private IGetImageLocation processLocation;

    public interface IGetImageLocation {
        void displayLocation(Location location);
    }

    public GetLocationTask(IGetImageLocation processLocation){
        this.processLocation = processLocation;
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
    protected Location doInBackground(String... params) {
        try {
            if (params != null && params.length > 0) {
                return astrobinWebServiceProxy.Location(Utils.extractId(params[0]), AstrobinWebServiceProxy.API_KEY_VALUE, AstrobinWebServiceProxy
                                .API_SECRET_VALUE,
                        AstrobinWebServiceProxy.FORMAT_VALUE);
            } else {
                return null;
            }
        } catch (Exception ex) {

        }

        return null;
    }

    @Override
    protected void onPostExecute(Location location) {
        processLocation.displayLocation(location);
    }
}
