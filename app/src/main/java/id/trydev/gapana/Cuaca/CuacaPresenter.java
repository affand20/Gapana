package id.trydev.gapana.Cuaca;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

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

import java.util.List;

public class CuacaPresenter {


    private CuacaView view;
    private Context context;

    private MapboxMap map;
    private MapView mapView;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;


    CuacaPresenter(CuacaView view, Context context){
        this.view = view;
        this.context = context;
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

    private void enableComponent(Style style) {
        if (PermissionsManager.areLocationPermissionsGranted(context)){
            
        }
    }
}
