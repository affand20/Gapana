package id.trydev.gapana.Cuaca.Api;

import id.trydev.gapana.Cuaca.Modal.Cuaca;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterfaceCuaca {
    @GET("{id}?apikey=hG0USgxrQHAxWP1Er8qY7n8ROo9aO7QR")
    Call<Cuaca> getForecast(@Path("id") String id);
}
