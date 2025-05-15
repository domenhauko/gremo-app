package si.uni_lj.fe.tnuv.gremov9;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import si.uni_lj.fe.tnuv.gremov9.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.mapView.onCreate(savedInstanceState);
        binding.mapView.getMapAsync(this);

        // Calendar button
        Button calendarButton = findViewById(R.id.calendarButton);
        calendarButton.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, CalendarActivity.class);
            startActivity(intent);
        });

        //Map button
        Button mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, MapsActivity.class);
            startActivity(intent);
        });

        //GREMO home button
        ImageButton gremoHomeButton = findViewById(R.id.gremoHomeButton);
        gremoHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    /// Manipulates the map once available.
    /// This callback is triggered when the map is ready to be used.
    /// This is where we can add markers or lines, add listeners or move the camera. In this case,
    /// we just add a marker near Sydney, Australia.
    /// If Google Play services is not installed on the device, the user will be prompted to install
    /// it inside the SupportMapFragment. This method will only be triggered once the user has
    /// installed Google Play services and returned to the app.
    @Override
    public void onMapReady(@NonNull GoogleMap map) {

        // Example: Add a marker and move the camera
        LatLng location = new LatLng(46.0569, 14.5058); // Ljubljana
        map.addMarker(new MarkerOptions().position(location).title("Marker in Ljubljana"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12));
    }

    // Forward lifecycle methods to the MapView
    @Override protected void onStart() { super.onStart(); binding.mapView.onStart(); }
    @Override protected void onResume() { super.onResume(); binding.mapView.onResume(); }
    @Override protected void onPause() { binding.mapView.onPause(); super.onPause(); }
    @Override protected void onStop() { binding.mapView.onStop(); super.onStop(); }
    @Override protected void onDestroy() { binding.mapView.onDestroy(); super.onDestroy(); }
    @Override public void onLowMemory() { super.onLowMemory(); binding.mapView.onLowMemory(); }
    @Override protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        binding.mapView.onSaveInstanceState(outState);
    }
}