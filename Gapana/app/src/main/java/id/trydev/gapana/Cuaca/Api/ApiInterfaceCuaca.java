package id.trydev.gapana.Cuaca.Api;

import id.trydev.gapana.Cuaca.Modal.Cuaca;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterfaceCuaca {
    @GET("{id}?apikey=bpqG0rwFlwUtBpYkjnMUGX2TduGSwSdf")
    Call<Cuaca> getForecast(@Path("id") String id);
}
