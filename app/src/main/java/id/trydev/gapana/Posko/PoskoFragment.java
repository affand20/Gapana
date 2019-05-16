package id.trydev.gapana.Posko;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import static com.mapbox.api.directions.v5.DirectionsCriteria.GEOMETRY_POLYLINE;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.directions.v5.models.LegStep;
import com.mapbox.core.constants.Constants;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.offline.OfflineManager;
import com.mapbox.mapboxsdk.offline.OfflineRegion;
import com.mapbox.mapboxsdk.offline.OfflineRegionError;
import com.mapbox.mapboxsdk.offline.OfflineRegionStatus;
import com.mapbox.mapboxsdk.offline.OfflineTilePyramidRegionDefinition;
import com.mapbox.mapboxsdk.plugins.offline.model.NotificationOptions;
import com.mapbox.mapboxsdk.plugins.offline.model.OfflineDownloadOptions;
import com.mapbox.mapboxsdk.plugins.offline.offline.OfflinePlugin;
import com.mapbox.mapboxsdk.plugins.offline.utils.OfflineUtils;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import id.trydev.gapana.Base.MainActivity;
import id.trydev.gapana.BuildConfig;
import id.trydev.gapana.Cuaca.CuacaPresenter;
import id.trydev.gapana.Cuaca.CuacaView;
import id.trydev.gapana.Posko.Model.NomorPenting;
import id.trydev.gapana.R;
import id.trydev.gapana.Utils.ItemClickSupport;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static android.support.constraint.Constraints.TAG;
import static com.mapbox.api.directions.v5.DirectionsCriteria.GEOMETRY_POLYLINE6;
import static com.mapbox.mapboxsdk.style.layers.Property.LINE_CAP_ROUND;
import static com.mapbox.mapboxsdk.style.layers.Property.LINE_JOIN_ROUND;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineOpacity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;
import static id.trydev.gapana.Base.MainActivity.db;
import static id.trydev.gapana.Base.MainActivity.preferences;

public class PoskoFragment extends Fragment implements OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener {

    private MapView mapView;
    private MapboxMap mapboxMap;
    private Context context;
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;
    private LocationEngine locationEngine;
    private OfflineManager offlineManager;
    private final float MIN_DISTANCE = 6;
    private Location lastLocation;
    private MapboxDirections mapboxDirectionsClient;
    private Handler handler = new Handler();
    private Runnable runnable;
    private FloatingActionButton lokasiTerkini, teleponDarurat;

    private PoskoAdapter adapter;
    private List<NomorPenting> listNomorPenting = new ArrayList<>();

    private double lastLatitude, lastLongitude;

    private static final float NAVIGATION_LINE_WIDTH = 6;
    private static final float NAVIGATION_LINE_OPACITY = .8f;
    private static final String DRIVING_ROUTE_POLYLINE_LINE_LAYER_ID = "DRIVING_ROUTE_POLYLINE_LINE_LAYER_ID";
    private static final String DRIVING_ROUTE_POLYLINE_SOURCE_ID = "DRIVING_ROUTE_POLYLINE_SOURCE_ID";
    private static final int DRAW_SPEED_MILLISECONDS = 500;

    private NomorPenting nomorPenting;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posko, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity().getApplicationContext();

        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);

        lokasiTerkini = view.findViewById(R.id.btn_lokasi_terkini);
        teleponDarurat = view.findViewById(R.id.btn_telepon);

        lokasiTerkini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(lastLatitude,lastLongitude),12
                ),5000);
            }
        });

        teleponDarurat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.layout_dialog_telepon_penting);

                RecyclerView rvNomorPenting = dialog.findViewById(R.id.list_nomor_penting);
                rvNomorPenting.setLayoutManager(new LinearLayoutManager(getActivity()));
                listNomorPenting = db.nomorPentingDao().getAll();
                adapter = new PoskoAdapter(listNomorPenting);
                rvNomorPenting.setAdapter(adapter);

                ItemClickSupport.addTo(rvNomorPenting).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        nomorPenting = listNomorPenting.get(position);

                        if (Build.VERSION.SDK_INT > 22){
                            if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                                startActivity(new Intent(
                                        Intent.ACTION_CALL, Uri.parse("tel:"+nomorPenting.getNomor())
                                ));
                            } else{
                                ActivityCompat.requestPermissions(
                                        getActivity(),
                                        new String[]{Manifest.permission.CALL_PHONE},
                                        100);
                            }
                        } else{
                            startActivity(new Intent(
                                    Intent.ACTION_CALL, Uri.parse("tel:"+nomorPenting.getNomor())
                            ));
                        }


                    }
                });

                dialog.show();
            }
        });

        locationEngine = LocationEngineProvider.getBestLocationEngine(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},101);

        } else{
            locationEngine.getLastLocation(new LocationEngineCallback<LocationEngineResult>() {
                @Override
                public void onSuccess(LocationEngineResult result) {
                    Location lastLocation = result.getLastLocation();
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
    public boolean onMapClick(@NonNull LatLng point) {
        Log.d(TAG, "Last Latitude onClick "+lastLatitude);
        Log.d(TAG, "Last Longitude onClick "+lastLongitude);
        getDirectionRoute(Point.fromLngLat(lastLongitude, lastLatitude), Point.fromLngLat(point.getLongitude(), point.getLatitude()));
        return false;
    }

    private void getDirectionRoute(Point origin, Point destination){
        mapboxDirectionsClient = MapboxDirections.builder()
                .origin(origin)
                .destination(destination)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .geometries(GEOMETRY_POLYLINE)
                .alternatives(true)
                .steps(true)
                .accessToken(BuildConfig.TOKEN)
                .build();

        mapboxDirectionsClient.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                // Create log messages in case no response or routes are present
                if (response.body() == null) {
                    Timber.d("No routes found, make sure you set the right user and access token.");
                    return;
                } else if (response.body().routes().size() < 1) {
                    Timber.d("No routes found");
                    return;
                }

// Get the route from the Mapbox Directions API response
                DirectionsRoute currentRoute = response.body().routes().get(0);
                Log.d(TAG, "onResponse: "+currentRoute);

// Start the step-by-step process of drawing the route
                runnable = new DrawRouteRunnable(mapboxMap, currentRoute.legs().get(0).steps(), handler);
                handler.postDelayed(runnable, DRAW_SPEED_MILLISECONDS);
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private static class DrawRouteRunnable implements Runnable {
        private MapboxMap mapboxMap;
        private List<LegStep> steps;
        private List<Feature> drivingRoutePolyLineFeatureList;
        private Handler handler;
        private int counterIndex;

        DrawRouteRunnable(MapboxMap mapboxMap, List<LegStep> steps, Handler handler) {
            this.mapboxMap = mapboxMap;
            this.steps = steps;
            this.handler = handler;
            this.counterIndex = 0;
            drivingRoutePolyLineFeatureList = new ArrayList<>();
        }

        @Override
        public void run() {
            if (counterIndex < steps.size()) {
                LegStep singleStep = steps.get(counterIndex);
                if (singleStep != null && singleStep.geometry() != null) {
                    LineString lineStringRepresentingSingleStep = LineString.fromPolyline(
                            singleStep.geometry(), Constants.PRECISION_5);
                    Feature featureLineString = Feature.fromGeometry(lineStringRepresentingSingleStep);
                    drivingRoutePolyLineFeatureList.add(featureLineString);
                }
                if (mapboxMap.getStyle() != null) {
                    GeoJsonSource source = mapboxMap.getStyle().getSourceAs(DRIVING_ROUTE_POLYLINE_SOURCE_ID);
                    if (source != null) {
                        source.setGeoJson(FeatureCollection.fromFeatures(drivingRoutePolyLineFeatureList));
                    }
                }
                counterIndex++;
                handler.postDelayed(this, DRAW_SPEED_MILLISECONDS);
            }
        }
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.TRAFFIC_DAY, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);

                List<Feature> markerCoordinates = new ArrayList<>();
                markerCoordinates.add(Feature.fromGeometry(
                        Point.fromLngLat(112.729967,-7.287692 )));
                markerCoordinates.add(Feature.fromGeometry(
                        Point.fromLngLat(112.728317,-7.296143 )));
                markerCoordinates.add(Feature.fromGeometry(
                        Point.fromLngLat(112.730536,-7.287010 )));

                style.addSource(new GeoJsonSource("marker-source",
                        FeatureCollection.fromFeatures(markerCoordinates)));

// Add the marker image to map
                style.addImage("my-marker-image", BitmapFactory.decodeResource(
                        getActivity().getResources(), R.drawable.mapbox_marker_icon_default));

// Adding an offset so that the bottom of the blue icon gets fixed to the coordinate, rather than the
// middle of the icon being fixed to the coordinate point.
                style.addLayer(new SymbolLayer("marker-layer", "marker-source")
                        .withProperties(PropertyFactory.iconImage("my-marker-image"),
                                iconOffset(new Float[]{0f, -9f})));

// Add the selected marker source and layer
                style.addSource(new GeoJsonSource("selected-marker"));

// Adding an offset so that the bottom of the blue icon gets fixed to the coordinate, rather than the
// middle of the icon being fixed to the coordinate point.
                style.addLayer(new SymbolLayer("selected-marker-layer", "selected-marker")
                        .withProperties(PropertyFactory.iconImage("my-marker-image"),
                                iconOffset(new Float[]{0f, -9f})));

                style.addSource(new GeoJsonSource(DRIVING_ROUTE_POLYLINE_SOURCE_ID));
                style.addLayerBelow(new LineLayer(DRIVING_ROUTE_POLYLINE_LINE_LAYER_ID, DRIVING_ROUTE_POLYLINE_SOURCE_ID)
                        .withProperties(
                                lineWidth(NAVIGATION_LINE_WIDTH),
                                lineOpacity(NAVIGATION_LINE_OPACITY),
                                lineCap(LINE_CAP_ROUND),
                                lineJoin(LINE_JOIN_ROUND),
                                lineColor(getActivity().getResources().getColor(R.color.colorPrimaryDark))
                        ), "marker-layer");

                mapboxMap.addOnMapClickListener(PoskoFragment.this);

                offlineManager = OfflineManager.getInstance(getActivity());
                LatLngBounds latLngBounds = new LatLngBounds.Builder()
                        .include(new LatLng(-8.9271, 111.1981))
                        .include(new LatLng(-6.2994, 114.6273))
//                        .include(new LatLng(-7.3735920, 112.6389210))
//                        .include(new LatLng(37.6744, -119.6815))
                        .build();

                OfflineTilePyramidRegionDefinition definition = new OfflineTilePyramidRegionDefinition(
                        mapboxMap.getStyle().getUrl(),
                        latLngBounds,
                        10,
                        20,
                        getActivity().getResources().getDisplayMetrics().density
                );



//                byte[] metadata;
//                try {
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("FIELD_REGION_NAME", "Surabaya");
//                    String json = jsonObject.toString();
//                    metadata = json.getBytes("UTF-8");
//                } catch (Exception exception) {
//                    Timber.e("Failed to encode metadata: %s", exception.getMessage());
//                    metadata = null;
//                }
////
//                offlineManager.createOfflineRegion(
//                        definition,
//                        metadata,
//                        new OfflineManager.CreateOfflineRegionCallback() {
//                            @Override
//                            public void onCreate(OfflineRegion offlineRegion) {
//                                offlineRegion.setDownloadState(OfflineRegion.STATE_ACTIVE);
//
//                                offlineRegion.setObserver(new OfflineRegion.OfflineRegionObserver() {
//                                    @Override
//                                    public void onStatusChanged(OfflineRegionStatus status) {
//                                        double percentage = status.getRequiredResourceCount() >= 0
//                                                ? (100.0 * status.getCompletedResourceCount() / status.getRequiredResourceCount()) :
//                                                0.0;
//                                        Log.d(TAG, "onStatusChanged: "+percentage);
//                                        Toast.makeText(getActivity(), "percentage: "+percentage, Toast.LENGTH_SHORT).show();
//                                        if (status.isComplete()){
//                                            Toast.makeText(getActivity(), "Region downloaded successfully", Toast.LENGTH_SHORT).show();
//                                            Log.d(TAG, "Region downloaded successfully.");
//                                        } else if (status.isRequiredResourceCountPrecise()){
//                                            Log.d(TAG, ""+percentage);
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onError(OfflineRegionError error) {
//                                        Log.e(TAG, "onError reason: "+error.getReason());
//                                        Log.e(TAG, "onError message: "+error.getMessage());
//                                    }
//
//                                    @Override
//                                    public void mapboxTileCountLimitExceeded(long limit) {
//                                        Timber.e("mapboxTileCountLimitExceeded: %s", limit);
//                                    }
//                                });
//                            }
//
//                            @Override
//                            public void onError(String error) {
//                                Timber.e("onError: %s", error);
//                            }
//                        });
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
        if (requestCode==100){
            if (grantResults!=null && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                startActivity(new Intent(
                        Intent.ACTION_CALL, Uri.parse("tel:"+nomorPenting.getNomor())
                ));
            }
        }
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Posko Evakuasi");
        ((MainActivity) getActivity()).setNavigationItemSelected(R.id.posko_evakuasi);
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
//        if (offlineManager!=null){
//            offlineManager.listOfflineRegions(new OfflineManager.ListOfflineRegionsCallback() {
//                @Override
//                public void onList(OfflineRegion[] offlineRegions) {
//                    if (offlineRegions.length>0){
//                        offlineRegions[(offlineRegions.length-1)].delete(new OfflineRegion.OfflineRegionDeleteCallback() {
//                            @Override
//                            public void onDelete() {
//                                Toast.makeText(getActivity(), "Region Deleted", Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onError(String error) {
//                                Timber.e("On delete error: %s",error);
//                            }
//                        });
//                    }
//                }
//
//                @Override
//                public void onError(String error) {
//                    Timber.e("onListError: %s", error);
//                }
//            });
//        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        if (handler!=null){
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mapboxDirectionsClient!=null){
            mapboxDirectionsClient.cancelCall();
        }
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
