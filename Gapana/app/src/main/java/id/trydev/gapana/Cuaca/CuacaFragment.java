package id.trydev.gapana.Cuaca;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.ArrayList;
import java.util.List;
import id.trydev.gapana.Base.MainActivity;
import id.trydev.gapana.Cuaca.Modal.Cuaca;
import id.trydev.gapana.Cuaca.Modal.Location;
import id.trydev.gapana.R;

import static android.support.constraint.Constraints.TAG;
import static id.trydev.gapana.Base.MainActivity.preferences;

public class CuacaFragment extends Fragment implements CuacaView, OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener {

    private MapView mapView;
    private MapboxMap mapboxMap;
    private Context context;
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;
    private LocationEngine locationEngine;
    private double lastLatitude, lastLongitude;

    private double lat, lng;

    private CuacaPresenter presenter;
    private RelativeLayout bgCuaca;
    private TextView kota, derajat, cuaca;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cuaca, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity().getApplicationContext();

        bgCuaca = view.findViewById(R.id.bgCuaca);
        kota = view.findViewById(R.id.kota);
        derajat = view.findViewById(R.id.derajat);
        cuaca = view.findViewById(R.id.cuaca);
        progressBar = view.findViewById(R.id.progressBar);

        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);

        presenter = new CuacaPresenter(this, context);
        presenter.getDataCuaca();

        locationEngine = LocationEngineProvider.getBestLocationEngine(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},101);

        } else{
            locationEngine.getLastLocation(new LocationEngineCallback<LocationEngineResult>() {
                @Override
                public void onSuccess(LocationEngineResult result) {
                    android.location.Location lastLocation = result.getLastLocation();
                    lastLatitude = lastLocation.getLatitude();
                    lastLongitude = lastLocation.getLongitude();
                    preferences.setLastLatitude(String.valueOf(lastLatitude));
                    preferences.setLastLongitude(String.valueOf(lastLongitude));

                }

                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(context, exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        Log.d(TAG, "Last Latitude "+lastLatitude);
        Log.d(TAG, "Last Longitude "+lastLongitude);

    }


    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        return false;
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.TRAFFIC_DAY, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);
            }
        });
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_cloud_queue_black_24dp);

        IconFactory iconFactory = IconFactory.getInstance(getContext());
        Icon icon = iconFactory.fromResource(R.drawable.cloud);

        presenter.getLocation();
        presenter.getDataCuaca();

        this.mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                presenter.getDataCuaca(marker.getTitle());
//                Toast.makeText(context, marker.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle){
        if (PermissionsManager.areLocationPermissionsGranted(context)){
            locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(getActivity().getApplicationContext(), loadedMapStyle);
            locationComponent.setLocationComponentEnabled(true);

            locationComponent.setCameraMode(CameraMode.TRACKING);
        } else{
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(context, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted){
            enableLocationComponent(mapboxMap.getStyle());
        } else{
            Toast.makeText(context, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Beranda");
        ((MainActivity) getActivity()).setNavigationItemSelected(R.id.beranda);
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showData(Cuaca cuaca, Location location) {
        if (bgCuaca.getVisibility() == View.GONE){
            bgCuaca.setVisibility(View.VISIBLE);
        }
        bgCuaca.setVisibility(View.VISIBLE);
        int icon = cuaca.getDailyForecasts().get(0).getDay().getIcon();
        if (icon < 6){
            bgCuaca.setBackgroundResource(R.drawable.cerah);
            this.cuaca.setText("CERAH");
        } else if (icon < 12){
            bgCuaca.setBackgroundResource(R.drawable.berawan);
            this.cuaca.setText("BERAWAWN");
        } else {
            bgCuaca.setBackgroundResource(R.drawable.hujan);
            this.cuaca.setText("HUJAN");
        }
        kota.setText(location.getNama());
        int minimum = Math.round((cuaca.getDailyForecasts().get(0).getTemperature().getMinimum().getValue() - 32) * 5/9);
        double maximum = Math.round((cuaca.getDailyForecasts().get(0).getTemperature().getMaximum().getValue() - 32) * 5/9);

        derajat.setText(String.valueOf(minimum) + " - " + String.valueOf(maximum) + " C");
    }

    @Override
    public void emptyData(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLocation(List<Location> list) {
        for (int i = 0; i < list.size(); i++) {
            IconFactory iconFactory = IconFactory.getInstance(getContext());
            Icon icon = iconFactory.fromResource(R.drawable.cloud);

            this.mapboxMap.addMarker(
                    new MarkerOptions()
                            .position(new LatLng(Double.valueOf(list.get(i).getLatitude()), Double.valueOf(list.get(i).getLongitude())))
                            .icon(icon)
                            .title(list.get(i).getNama())
            );
        }
    }
}
