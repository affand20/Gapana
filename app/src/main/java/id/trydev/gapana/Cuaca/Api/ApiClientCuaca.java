package id.trydev.gapana.Cuaca.Api;

import retrofit2.Retrofit;

public class ApiClientCuaca {
    public static Retrofit retrofit = null;
    public  static Retrofit getClient(){
        return retrofit;
    }
}
