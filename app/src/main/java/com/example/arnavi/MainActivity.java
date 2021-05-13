package com.example.arnavi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;

import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.directions.v5.MapboxDirections;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
//import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
//import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
//import com.mapbox.navigation.ui.route.NavigationMapRoute;

import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
//import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
//import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

public class MainActivity extends AppCompatActivity implements PermissionsListener, OnMapReadyCallback {

    // Variables needed to initialize a map
    private MapboxMap mapboxMap;
    private MapView mapView;
    private Button activateButton;

    // Variables needed to handle location permissions
    private PermissionsManager permissionsManager;

    // Variables needed to add the location engine
    LocationEngine locationEngine;
    long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;

    // Variables needed to listen to location updates
    LocationListeningCallback callback = new LocationListeningCallback(this);
    private static final String TAG = "MainActivity_location";

    public static double La;    //latitude
    public static double Lo;    // longitude

    private double[] LaLo;

    // Varibales needed to Navigation
    private Point origin;// = Point.fromLngLat(126.8876249976178, 35.179352364881765);
    private Point destination;
    private NavigationMapRoute navigationMapRoute;
    public static DirectionsRoute currentRoute;
    private MapboxDirections client;
    private Button startButton;

    LocationComponent locationComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate실행");
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));  //MapBox 접근을 위한 access_token 지정

        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if(intent != null){
            LaLo = intent.getDoubleArrayExtra("LaLo");
        }

        destination = Point.fromLngLat(LaLo[0], LaLo[1]);
        //Setup the Destination Poing
//        destination = Point.fromLngLat(35.1419225, 126.9321397);

        //Setup the MapView
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        activateButton = findViewById(R.id.activateButton);
        activateButton.setEnabled(true);
        activateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                origin = Point.fromLngLat(Lo, La);
                getRoute_navi_walking(origin, destination);
                activateButton.setVisibility(View.INVISIBLE);
                startButton.setEnabled(true);
            }
        });

        startButton = findViewById(R.id.btnStartNavigation);
        startButton.setEnabled(false);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean simulateRoute = true;
                NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                        .directionsRoute(currentRoute)
                        .shouldSimulateRoute(simulateRoute)
                        .build();
                // Call this method with Context from within an Activity
                NavigationLauncher.startNavigation(MainActivity.this, options);
            }
        });

    }

    @Override   //Location Permissions
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override   //Messages that come out when you need permission.
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.e(TAG, "onActivityResult 실행");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                editText.setText(result.get(0));
//                STT.setText(result.get(0));
                startButton.setEnabled(true);
//                arstartButton.setEnabled(true);
//                getPointFromGeoCoder(editText.getText().toString());
                Point origin = Point.fromLngLat(Lo, La);
//                Point destination = Point.fromLngLat(destinationX, destinationY);
//                getRoute_walking(origin,destination);//폴리라인 그리기
                getRoute_navi_walking(origin, destination);
            }
        }
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        Log.e(TAG, "onMapReady실행");
        this.mapboxMap = mapboxMap;

        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/cjsthd88/cko3ufmwr0b3x18pv1j7janpt"), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                Log.e(TAG, "onStyleLoaded실행");
                // Map is set up and the style has loaded. Now you can add data or make other map adjustments
                enableLocationComponent(style);
            }
        });

//        getRoute_walking(origin, destination);
//        getRoute_navi_walking(origin, destination);
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            if (mapboxMap.getStyle() != null) {
                enableLocationComponent(mapboxMap.getStyle());
            } else {    // User denied the permission
                Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }


    private static class LocationListeningCallback
            implements LocationEngineCallback<LocationEngineResult> {

        private final WeakReference<MainActivity> activityWeakReference;

        LocationListeningCallback(MainActivity activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        // The LocationEngineCallback interface's method which fires when the device's location has changed.
        // @param result the LocationEngineResult object which has the last known location within it.
        @Override
        public void onSuccess(LocationEngineResult result) {
            Log.e(TAG, "onSuccess 실행");
            MainActivity activity = activityWeakReference.get();
            if (activity != null) {
                Location location = result.getLastLocation();
                if (location == null) {
                    return;
                }
                // Create a Toast which displays the new location's coordinates
                La = result.getLastLocation().getLatitude();
                Lo = result.getLastLocation().getLongitude();

                // Pass the new location to the Maps SDK's LocationComponent
                if (activity.mapboxMap != null && result.getLastLocation() != null) {
                    activity.mapboxMap.getLocationComponent().forceLocationUpdate(result.getLastLocation());
                }
            }
        }

        // The LocationEngineCallback interface's method which fires when the device's location can not be captured
        // @param exception the exception message
        @Override
        public void onFailure(@NonNull Exception exception) {
            Log.e("LocationChangeActivity", exception.getLocalizedMessage());
            MainActivity activity = activityWeakReference.get();
            if (activity != null) {
                Toast.makeText(activity, exception.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Initialize the Maps SDK's LocationComponent
    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        Log.e(TAG, "enableLocationComponent 실행");
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            // Get an instance of the component
            locationComponent = mapboxMap.getLocationComponent();

            // Set the LocationComponent activation options
            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(this, loadedMapStyle)
                            .useDefaultLocationEngine(false)
                            .build();

            // Activate with the LocationComponentActivationOptions object
            locationComponent.activateLocationComponent(locationComponentActivationOptions);

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            // Tracking Mode : camera tracks the user location
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            // Compass Mode : Shows user location with bearing considered from compass
            locationComponent.setRenderMode(RenderMode.COMPASS);

            initLocationEngine();


        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    // Set up the LocationEngine and the parameters for querying the device's location
    @SuppressLint("MissingPermission")
    private void initLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(this);

        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_NO_POWER)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME)
                .build();

        locationEngine.requestLocationUpdates(request, callback, getMainLooper());
        locationEngine.getLastLocation(callback);

    }

//    private void getRoute_walking(Point origin, Point destination) {
//        Log.e(TAG, "getRoute 실행");
//        client = MapboxDirections.builder()
//                .origin(origin)//출발지 위도 경도
//                .destination(destination)//도착지 위도 경도
//                .overview(DirectionsCriteria.OVERVIEW_FULL)//정보 받는정도 최대
//                .profile(DirectionsCriteria.PROFILE_WALKING)//길찾기 방법(도보,자전거,자동차)
//                .accessToken(getString(R.string.mapbox_access_token))
//                .build();
//
//        client.enqueueCall(new Callback<DirectionsResponse>() {
//            @Override
//            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
//                Log.e(TAG, "onResponse 실행");
//                System.out.println(call.request().url().toString());
//                // You can get the generic HTTP info about the response
//                Log.e(TAG, "Response code: " + response.code());
//                if (response.body() == null) {
//                    Log.e(TAG, "No routes found, make sure you set the right user and access token.");
//                    return;
//                } else if (response.body().routes().size() < 1) {
//                    Log.e(TAG, "No routes found");
//                    return;
//                }
//                // Print some info about the route
//                currentRoute = response.body().routes().get(0);
//                Log.e(TAG, "Distance: " + currentRoute.distance());
//
//                int time = (int) (currentRoute.duration() / 60);
//                //예상 시간을초단위로 받아옴
//                double distants = (currentRoute.distance() / 1000);
//                //목적지까지의 거리를 m로 받아옴
//
//                distants = Math.round(distants * 100) / 100.0;
//                //Math.round() 함수는 소수점 첫째자리에서 반올림하여 정수로 남긴다
//                //원래 수에 100곱하고 round 실행 후 다시 100으로 나눈다 -> 둘째자리까지 남김
//
//                Toast.makeText(getApplicationContext(), String.format("예상 시간 : " + String.valueOf(time) + " 분 \n" +
//                        "목적지 거리 : " + distants + " km"), Toast.LENGTH_LONG).show();
//                // Draw the route on the map
//                //drawRoute(currentRoute);
//            }
//
//            @Override
//            public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
//
//            }
//        });
//    }

    private void getRoute_navi_walking(Point ori, Point dest) {
        Log.e(TAG, "get_Route_navi_walking실행");
        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())  //R.string.mapbox_access_token)
                .origin(ori)
                .destination(dest)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        // You can get the generic HTTP info about the response
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }

                        currentRoute = response.body().routes().get(0);

                        // Draw the route on the map
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        //prevent memory leakage
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates(callback);
        }

        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

}