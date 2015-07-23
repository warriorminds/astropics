package com.warriorminds.astropics.retrofit;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by rodrigo.guerrero on 7/20/2015.
 */
public class RetrofitErrorHandler implements ErrorHandler {
    @Override public Throwable handleError(RetrofitError cause) {
        Response r = cause.getResponse();
        if (r != null && r.getStatus() == 401) {
            return new Exception(cause);
        }
        return cause;
    }
}
