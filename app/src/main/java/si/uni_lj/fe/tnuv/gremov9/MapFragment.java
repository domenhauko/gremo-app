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

        // Observe date changes
        sharedViewModelDate.getSelectedDate().observe(getViewLifecycleOwner(), new Observer<LocalDate>() {
            @Override
            public void onChanged(LocalDate date) {
                if (googleMap != null) {
                    updateMapForDate(date);
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;

        // Set initial map position
        LatLng ljubljana = new LatLng(46.0569, 14.5058);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ljubljana, 13f));

        // If date already selected, load markers
        LocalDate selectedDate = sharedViewModelDate.getSelectedDate().getValue();
        if (selectedDate != null) {
            updateMapForDate(selectedDate);
        }
    }

    private void updateMapForDate(LocalDate date) {
        googleMap.clear();

        // Sample events for the selected date
        List<Event> events = EventRepository.getEventsForDate(date); // Replace with your data source

        for (Event event : events) {
            LatLng eventLocation = new LatLng(event.getLatitude(), event.getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(eventLocation)
                    .title(event.getTitle()));
        }
    }
}
