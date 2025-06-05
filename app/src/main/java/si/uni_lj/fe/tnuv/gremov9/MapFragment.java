package si.uni_lj.fe.tnuv.gremov9;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap googleMap;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getMapAsync(this);  // triggers map load
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        // Set initial position and zoom
        LatLng location = new LatLng(46.0569, 14.5058); // Example: Ljubljana
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f));

        // Optional marker
        googleMap.addMarker(new MarkerOptions().position(location).title("Event Location"));
    }
}