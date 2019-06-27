package id.trydev.gapana.Cuaca.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientCuaca {
    public static String BASE_URL = "https://dataservice.accuweather.com/forecasts/v1/daily/1day/";
    public static Retrofit retrofit = null;
    public  static Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
