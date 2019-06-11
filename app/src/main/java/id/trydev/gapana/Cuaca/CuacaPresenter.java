package id.trydev.gapana.Cuaca;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.trydev.gapana.Cuaca.Api.ApiClientCuaca;
import id.trydev.gapana.Cuaca.Api.ApiInterfaceCuaca;
import id.trydev.gapana.Cuaca.Modal.Cuaca;
import id.trydev.gapana.Cuaca.Modal.DailyForecasts;
import id.trydev.gapana.Cuaca.Modal.Headline;
import id.trydev.gapana.Cuaca.Modal.Location;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CuacaPresenter {

    private CuacaView view;
    private Context context;

    private MapboxMap map;
    private MapView mapView;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    ApiInterfaceCuaca mApiInterface;
    private List<Location> list;

    CuacaPresenter(CuacaView view, Context context){
        this.view = view;
        this.context = context;
        list = new ArrayList<>();
        mApiInterface = ApiClientCuaca.getClient().create(ApiInterfaceCuaca.class);
    }

    void getLocation(){
        list.add(
                new Location("203449", "Surabaya", "-7.257472", "112.752090")
        );
        list.add(
                new Location("211288", "Palembang", "-2.977", "104.742")
        );
        list.add(
                new Location("211298", "Medan", "3.592", "98.68")
        );
        list.add(
                new Location("206050", "Manado", "1.543", "124.922")
        );
        list.add(
                new Location("3468550", "Bali", "-8.531", "118.461")
        );
        list.add(
                new Location("211671", "Yogyakarta", "-7.792", "110.368")
        );
        list.add(
                new Location("208971", "Jakarta", "-6.175", "106.827")
        );
        list.add(
                new Location("208977", "Bandung", "-6.904", "107.61")
        );
        list.add(
                new Location("208981", "Semarang", "-7.059", "110.433")
        );
        list.add(
                new Location("203178", "Banyuwangi", "-8.212", "114.376")
        );
        list.add(
                new Location("3449051", "Mataram", "-5.333", "105.042")
        );
        list.add(
                new Location("203752", "Balikpapan", "-1.271", "116.838")
        );
        list.add(
                new Location("209036", "Banjarmasin", "-3.322", "114.603")
        );
        list.add(
                new Location("209030", "Pontianak", "-0.028", "109.341")
        );
        list.add(
                new Location("205619", "Pekanbaru", "0.499", "101.418")
        );
        list.add(
                new Location("206120", "Padang", "-0.959", "100.36")
        );
        list.add(
                new Location("211242", "Makassar", "-5.184", "119.441")
        );
        list.add(
                new Location("205850", "Palu", "-0.891", "119.872")
        );
        list.add(
                new Location("205960", "Kendari", "-3.986", "122.515")
        );
        list.add(
                new Location("2-205731_1_AL", "Pare-Pare", "-4.04", "119.627")
        );
        list.add(
                new Location("205207", "Sumbawa", "-8.498", "117.426")
        );
        list.add(
                new Location("203182", "Madiun", "-7.644", "111.531")
        );
        view.showLocation(list);
    }

    void showCurrentLocation(MapView mapView){
        MapboxMapOptions options = new MapboxMapOptions();
        options.camera(new CameraPosition.Builder()
                .target(new LatLng(112.6356093, -7.3733786))
                .zoom(9)
                .build());

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.LIGHT, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        enableComponent(style);
                    }
                });
            }
        });
    }

    void getDataCuaca(){
        view.showLoading();
        Call<Cuaca> call = mApiInterface.getForecast("203449");
        call.enqueue(new Callback<Cuaca>() {
            @Override
            public void onResponse(Call<Cuaca> call, Response<Cuaca> response) {
                view.showData(response.body(), list.get(0));
                view.dismissLoading();
            }

            @Override
            public void onFailure(Call<Cuaca> call, Throwable t) {
                view.emptyData("Gagal load cuaca");
                view.dismissLoading();
                Log.e("failure", "cuaca presenter " + t.getMessage().toString());
            }
        });
        Log.e("call api", "Berhasil");
    }

    void getDataCuaca(String title){
        view.showLoading();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getNama().equals(title)){
                Call<Cuaca> call = mApiInterface.getForecast(list.get(i).getKey());
                Location loc = list.get(i);
                call.enqueue(new Callback<Cuaca>() {
                    @Override
                    public void onResponse(Call<Cuaca> call, Response<Cuaca> response) {
                        List<DailyForecasts> list = response.body().getDailyForecasts();
                        int a = list.get(0).getTemperature().getMaximum().getValue();
                        Log.e("cuaca stuan", String.valueOf(a));
                        view.showData(response.body(), loc);
                        view.dismissLoading();
                    }

                    @Override
                    public void onFailure(Call<Cuaca> call, Throwable t) {
                        Log.e("failure", "cuaca presenter " + t.getMessage().toString());
                        view.emptyData("Gagal load cuaca");
                        view.dismissLoading();
                    }
                });
            }
        }
    }

    private void enableComponent(Style style) {
        if (PermissionsManager.areLocationPermissionsGranted(context)){
            
        }
    }
}
