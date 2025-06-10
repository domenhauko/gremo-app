package si.uni_lj.fe.tnuv.gremov9;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.time.LocalDate;
import java.util.List;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private SharedViewModelDate sharedViewModelDate;

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        getMapAsync(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Initialize shared view model
        sharedViewModelDate = new ViewModelProvider(requireActivity()).get(SharedViewModelDate.class);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;

        // Set initial map position
        LatLng ljubljana = new LatLng(46.0569, 14.5058);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ljubljana, 13f));
    }
}