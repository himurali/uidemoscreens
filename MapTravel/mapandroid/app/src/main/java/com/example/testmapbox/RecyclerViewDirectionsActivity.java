package com.example.testmapbox;

import static com.mapbox.core.constants.Constants.PRECISION_6;
import static com.mapbox.mapboxsdk.style.layers.Property.LINE_JOIN_ROUND;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textField;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewDirectionsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "RVDirectionsActivity";
    private static final String SYMBOL_ICON_ID = "SYMBOL_ICON_ID";
    private static final String PERSON_ICON_ID = "PERSON_ICON_ID";
    private static final String MARKER_SOURCE_ID = "MARKER_SOURCE_ID";
    private static final String PERSON_SOURCE_ID = "PERSON_SOURCE_ID";
    private static final String DASHED_DIRECTIONS_LINE_LAYER_SOURCE_ID = "DASHED_DIRECTIONS_LINE_LAYER_SOURCE_ID";
    private static final String LAYER_ID = "LAYER_ID";
    private static final String PERSON_LAYER_ID = "PERSON_LAYER_ID";
    private static final String DASHED_DIRECTIONS_LINE_LAYER_ID = "DASHED_DIRECTIONS_LINE_LAYER_ID";
    private static final Point directionsOriginPoint = Point.fromLngLat(77.588115,
            12.947948);
    private static final  LatLng[] possibleDestinations = new LatLng[]{
            //,
            new LatLng(12.947948, 77.588115),
            new LatLng(12.947758, 77.588051),
            new LatLng(12.94693, 77.588133),
            new LatLng(12.948614, 77.58841            ),
            new LatLng(12.946564, 77.588069),
            new LatLng(12.946593, 77.587409),
            new LatLng(12.947705, 77.586527),
            new LatLng(12.94783, 77.586129),
            new LatLng(12.947628, 77.5857)
    };
    private static final  String[] possibleTrees = new String[]{
            new String ("Saraca asoca"),
            new String ("Goni ficus mysorensis\n"),
            new String ("Rudraksha"),
            new String ("Kigelia pinnata\n"),
            new String ("Elaeocarpus ganitirus\n"),
            new String ("Terminalia arjuna\n"),
            new String ("Albizia lebbeck\n"),
            new String ("Neolamarckia cadamba\n"),
            new String ("Acacia polyacantha\n")
    };

    private final List<DirectionsRoute> directionsRouteList = new ArrayList<>();
    private MapboxMap mapboxMap;
    private MapView mapView;
    private FeatureCollection dashedLineDirectionsFeatureCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        // This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_recyclerviewdirection);

        // Initialize the map view
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {


        RecyclerViewDirectionsActivity.this.mapboxMap = mapboxMap;

        RecyclerViewDirectionsActivity.this.mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
            @Override
            public boolean onMapClick(@NonNull LatLng point) {
                PointF screenPoint = mapboxMap.getProjection().toScreenLocation(point);
                List<Feature> features = mapboxMap.queryRenderedFeatures(screenPoint, LAYER_ID);
                if (!features.isEmpty()) {
                    Feature selectedFeature = features.get(0);
                    String title = selectedFeature.getStringProperty("title");
                    Toast.makeText(getApplicationContext(), "You selected " + title, Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        Integer ind2 = 9;
        String s ="3";
        mapboxMap.setStyle(new Style.Builder().fromUri(Style.LIGHT)

                // Set up the image, source, and layer for the person icon,
                // which is where all of the routes will start from
                .withImage(PERSON_ICON_ID, BitmapUtils.getBitmapFromDrawable(
                        getResources().getDrawable(R.drawable.ic_person)))
                .withSource(new GeoJsonSource(PERSON_SOURCE_ID,
                        Feature.fromGeometry(directionsOriginPoint)))
                .withLayer(new SymbolLayer(PERSON_LAYER_ID, PERSON_SOURCE_ID).withProperties(

                        //    iconImage(PERSON_ICON_ID),
                        textField(s),
                        iconSize(0.5f),
                        iconAllowOverlap(true),
                        iconIgnorePlacement(true)
                ))

                // Set up the image, source, and layer for the potential destination markers
                .withImage(SYMBOL_ICON_ID, BitmapFactory.decodeResource(
                        this.getResources(), R.drawable.red_marker))
                .withSource(new GeoJsonSource(MARKER_SOURCE_ID, initDestinationFeatureCollection()))
                .withLayer(new SymbolLayer(LAYER_ID, MARKER_SOURCE_ID).withProperties(
                        iconImage(SYMBOL_ICON_ID),
                        PropertyFactory.textField(Expression.get("marker-symbol")),
                        iconAllowOverlap(true),
                        iconIgnorePlacement(true),
                        iconOffset(new Float[]{0f, -4f})
                ))


                // Set up the source and layer for the direction route LineLayer
                .withSource(new GeoJsonSource(DASHED_DIRECTIONS_LINE_LAYER_SOURCE_ID))
                .withLayerBelow(
                        new LineLayer(DASHED_DIRECTIONS_LINE_LAYER_ID, DASHED_DIRECTIONS_LINE_LAYER_SOURCE_ID)
                                .withProperties(
                                        lineWidth(7f),
                                        lineJoin(LINE_JOIN_ROUND),
                                        lineColor(Color.parseColor("#2096F3"))
                                ), PERSON_LAYER_ID), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                getRoutesToAllPoints();
                initRecyclerView();
                Toast.makeText(RecyclerViewDirectionsActivity.this,
                        R.string.toast_instruction, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Loop through the possible destination list of LatLng locations and get
     * the route for each destination.
     */
    private void getRoutesToAllPoints() {
        for (LatLng singleLatLng : possibleDestinations) {
            getRoute(Point.fromLngLat(singleLatLng.getLongitude(), singleLatLng.getLatitude()));
        }
    }

    /**
     * Make a call to the Mapbox Directions API to get the route from the person location icon
     * to the marker's location and then add the route to the route list.
     *
     * @param destination the marker associated with the recyclerview card that was tapped on.
     */
    @SuppressWarnings({"MissingPermission"})
    private void getRoute(Point destination) {
        MapboxDirections client = MapboxDirections.builder()
                .origin(directionsOriginPoint)
                .destination(destination)
                .overview(DirectionsCriteria.OVERVIEW_SIMPLIFIED)
                .profile(DirectionsCriteria.PROFILE_WALKING)
                .accessToken(getString(R.string.mapbox_access_token))
                .build();
        client.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                if (response.body() == null) {
                    Log.d(TAG, "No routes found, make sure you set the right user and access token.");
                    return;
                } else if (response.body().routes().size() < 1) {
                    Log.d(TAG, "No routes found");
                    return;
                }
                // Add the route to the list.
                directionsRouteList.add(response.body().routes().get(0));
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                Log.d(TAG, "Error: " + throwable.getMessage());
                if (!throwable.getMessage().equals("Coordinate is invalid: 0,0")) {
                    Toast.makeText(RecyclerViewDirectionsActivity.this,
                            "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Update the GeoJSON data for the direction route LineLayer.
     *
     * @param route The route to be drawn in the map's LineLayer that was set up above.
     */
    private void drawNavigationPolylineRoute(final DirectionsRoute route) {
        if (mapboxMap != null) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    List<Feature> directionsRouteFeatureList = new ArrayList<>();
                    LineString lineString = LineString.fromPolyline(route.geometry(), PRECISION_6);
                    List<Point> lineStringCoordinates = lineString.coordinates();
                    for (int i = 0; i < lineStringCoordinates.size(); i++) {
                        directionsRouteFeatureList.add(Feature.fromGeometry(
                                LineString.fromLngLats(lineStringCoordinates)));
                    }
                    dashedLineDirectionsFeatureCollection =
                            FeatureCollection.fromFeatures(directionsRouteFeatureList);
                    GeoJsonSource source = style.getSourceAs(DASHED_DIRECTIONS_LINE_LAYER_SOURCE_ID);
                    if (source != null) {
                        source.setGeoJson(dashedLineDirectionsFeatureCollection);
                    }
                }
            });
        }
    }

    /**
     * Create a FeatureCollection to display the possible destination markers.
     *
     * @return a {@link FeatureCollection}, which represents the possible destinations.
     */
    private FeatureCollection initDestinationFeatureCollection() {
        List<Feature> featureList = new ArrayList<>();
        int index = 0 ;
        for (LatLng latLng : possibleDestinations) {

            Feature f = Feature.fromGeometry(
                    Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude()));
            f.addStringProperty("title",possibleTrees[index++]);
            f.addStringProperty("marker-symbol", String.valueOf(index));
            featureList.add(f);

        }
        return FeatureCollection.fromFeatures(featureList);
    }

    /**
     * Set up the RecyclerView.
     */
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rv_on_top_of_map);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new LocationRecyclerViewAdapter(this,
                createRecyclerViewLocations(), mapboxMap));
        new LinearSnapHelper().attachToRecyclerView(recyclerView);
    }

    /**
     * Create data fro the RecyclerView.
     *
     * @return a list of {@link SingleRecyclerViewLocation} objects for the RecyclerView.
     */
    private List<SingleRecyclerViewLocation> createRecyclerViewLocations() {
        ArrayList<SingleRecyclerViewLocation> locationList = new ArrayList<>();
        for (int x = 0; x < possibleDestinations.length; x++) {
            SingleRecyclerViewLocation singleLocation = new SingleRecyclerViewLocation();
            singleLocation.setName(String.format( possibleTrees[x], x));
            int y = x+1;
            singleLocation.setAvailableTables(String.format(getString(
                    R.string.rv_directions_route_available_table_info),
                    y));
            locationList.add(singleLocation);
        }
        return locationList;
    }

    /**
     * POJO model class for a single location in the RecyclerView.
     */
    class SingleRecyclerViewLocation {

        private String name;
        private String availableTables;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvailableTables() {
            return availableTables;
        }

        public void setAvailableTables(String availableTables) {
            this.availableTables = availableTables;
        }

    }

    static class LocationRecyclerViewAdapter extends
            RecyclerView.Adapter<LocationRecyclerViewAdapter.MyViewHolder> {

        private List<SingleRecyclerViewLocation> locationList;
        private MapboxMap map;
        private WeakReference<RecyclerViewDirectionsActivity> weakReference;
        private BottomSheetBehavior mBottomSheetBehavior;


        public LocationRecyclerViewAdapter(RecyclerViewDirectionsActivity activity,
                                           List<SingleRecyclerViewLocation> locationList,
                                           MapboxMap mapBoxMap) {
            this.locationList = locationList;
            this.map = mapBoxMap;
            this.weakReference = new WeakReference<>(activity);
            this.mBottomSheetBehavior = mBottomSheetBehavior;

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rv_directions_card, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            SingleRecyclerViewLocation singleRecyclerViewLocation = locationList.get(position);
            holder.name.setText(singleRecyclerViewLocation.getName());
            holder.numOfAvailableTables.setText(singleRecyclerViewLocation.getAvailableTables());
            holder.setClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position) {

                  /*  weakReference.get()
                            .drawNavigationPolylineRoute(weakReference.get().directionsRouteList.get(position));*/

                    LatLng selectedLocationLatLng = possibleDestinations[position] ;
                    CameraPosition newCameraPosition = new CameraPosition.Builder()
                            .target(selectedLocationLatLng)
                            .zoom(17)
                            .build();
                    map.easeCamera(CameraUpdateFactory.newCameraPosition(newCameraPosition));
                }
            });
        }

        @Override
        public int getItemCount() {
            return locationList.size();
        }

        static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView name;
            TextView numOfAvailableTables;
            CardView singleCard;

            ItemClickListener clickListener;

            MyViewHolder(View view) {
                super(view);
                name = view.findViewById(R.id.location_title_tv);
                numOfAvailableTables = view.findViewById(R.id.location_num_of_beds_tv);
                singleCard = view.findViewById(R.id.single_location_cardview);
                singleCard.setOnClickListener(this);
            }

            public void setClickListener(ItemClickListener itemClickListener) {
                this.clickListener = itemClickListener;
            }

            @Override
            public void onClick(View view) {
                clickListener.onClick(view, getLayoutPosition());
            }
        }
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    // Add the mapView lifecycle to the activity's lifecycle methods
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}