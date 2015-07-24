package com.warriorminds.astropics.retrofit;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by rodrigo.guerrero on 6/24/2015.
 */
public interface AstrobinWebServiceProxy {

    String ENDPOINT = "http://www.astrobin.com/api/v1";
    String API_KEY_VALUE = "YOUR API KEY VALUE HERE";
    String API_SECRET_VALUE = "YOUR API SECRET VALUE HERE";
    String FORMAT_VALUE = "json";

    // Parameters
    String API_KEY = "api_key";
    String API_SECRET = "api_secret";
    String FORMAT = "format";
    String SUBJECTS = "subjects";
    String USER = "user";
    String TITLE = "title__icontains";
    String DESCRIPTION = "description__icontains";
    String LIMIT = "limit";
    String OFFSET = "offset";

    @GET("/image/{id}")
    Image GetImage(@Path("id") String id, @Query(API_KEY) String apiKey, @Query(API_SECRET) String apiSecret, @Query(FORMAT) String format);

    @GET("/image")
    ImageList GetAllImages(@Query(API_KEY) String apiKey, @Query(API_SECRET) String apiSecret, @Query(FORMAT) String format);

    @GET("/image")
    ImageList GetAllImages(@Query(API_KEY) String apiKey, @Query(API_SECRET) String apiSecret, @Query(FORMAT) String format, @Query(OFFSET) int offset, @Query(LIMIT) int limit);

    @GET("/image")
    ImageList SearchBySubject(@Query(SUBJECTS) String subject, @Query(API_KEY) String apiKey, @Query(API_SECRET) String apiSecret, @Query(FORMAT) String format);

    @GET("/image")
    ImageList SearchBySubject(@Query(SUBJECTS) String subject, @Query(API_KEY) String apiKey, @Query(API_SECRET) String apiSecret, @Query(FORMAT) String format, @Query(OFFSET) int offset, @Query(LIMIT) int limit);

    @GET("/image")
    ImageList SearchByUser(@Query(USER) String user, @Query(API_KEY) String apiKey, @Query(API_SECRET) String apiSecret, @Query(FORMAT) String format);

    @GET("/image")
    ImageList SearchByTitle(@Query(TITLE) String title, @Query(API_KEY) String apiKey, @Query(API_SECRET) String apiSecret, @Query(FORMAT) String format);

    @GET("/image")
    ImageList SearchByTitle(@Query(TITLE) String title, @Query(API_KEY) String apiKey, @Query(API_SECRET) String apiSecret, @Query(FORMAT) String format, @Query(OFFSET) int offset, @Query(LIMIT) int limit);

    @GET("/image")
    ImageList SearchByDescription(@Query(DESCRIPTION) String description, @Query(API_KEY) String apiKey, @Query(API_SECRET) String apiSecret, @Query(FORMAT) String format);

    @GET("/imageoftheday")
    ImageOfTheDayResult ImageOfTheDay(@Query(API_KEY) String apiKey, @Query(API_SECRET) String apiSecret, @Query(FORMAT) String format);

    @GET("/location/{id}")
    Location Location(@Path("id") String id, @Query(API_KEY) String apiKey, @Query(API_SECRET) String apiSecret, @Query(FORMAT) String format);

}
