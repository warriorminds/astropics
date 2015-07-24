package com.warriorminds.astropics.tasks;

import android.os.AsyncTask;

import com.warriorminds.astropics.retrofit.AstrobinWebServiceProxy;
import com.warriorminds.astropics.retrofit.Image;
import com.warriorminds.astropics.retrofit.ImageOfTheDayResult;
import com.warriorminds.astropics.retrofit.Location;
import com.warriorminds.astropics.retrofit.RetrofitErrorHandler;
import com.warriorminds.astropics.utils.ImageOfTheDayLocation;
import com.warriorminds.astropics.utils.Utils;

import retrofit.RestAdapter;

public class GetImageOfTheDayTask extends AsyncTask<Void, Void, ImageOfTheDayLocation> {

    private AstrobinWebServiceProxy astrobinWebServiceProxy;

    public interface IImageOfTheDayResult {
        void displayImageOfTheDay(ImageOfTheDayLocation image);
    }

    private IImageOfTheDayResult iImageOfTheDayResult;

    public GetImageOfTheDayTask(IImageOfTheDayResult iImageOfTheDayResult){
        this.iImageOfTheDayResult = iImageOfTheDayResult;
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
    protected ImageOfTheDayLocation doInBackground(Void... params) {
        try {
            ImageOfTheDayResult imagesOfTheDay = astrobinWebServiceProxy.ImageOfTheDay(AstrobinWebServiceProxy.API_KEY_VALUE, AstrobinWebServiceProxy
                            .API_SECRET_VALUE,
                    AstrobinWebServiceProxy.FORMAT_VALUE);

            String iotd = imagesOfTheDay.getImagesList().get(0).getImage();
            String imageId = Utils.extractId(iotd);
            ImageOfTheDayLocation result = new ImageOfTheDayLocation();
            result.setImageOfTheDay(imagesOfTheDay.getImagesList().get(0));
            Image image = astrobinWebServiceProxy.GetImage(imageId, AstrobinWebServiceProxy.API_KEY_VALUE, AstrobinWebServiceProxy.API_SECRET_VALUE,
                    AstrobinWebServiceProxy.FORMAT_VALUE);

            if (image.getLocations() != null && image.getLocations().length > 0) {
                Location location = astrobinWebServiceProxy.Location(Utils.extractId(image.getLocations()[0]), AstrobinWebServiceProxy.API_KEY_VALUE, AstrobinWebServiceProxy
                                .API_SECRET_VALUE,
                        AstrobinWebServiceProxy.FORMAT_VALUE);
                result.setLocation(location);
            }
            result.setImage(image);


            return result;
        } catch (Exception ex) {

        }

        return null;
    }

    @Override
    protected void onPostExecute(ImageOfTheDayLocation imageOfTheDayLocation) {
        iImageOfTheDayResult.displayImageOfTheDay(imageOfTheDayLocation);
    }
}
