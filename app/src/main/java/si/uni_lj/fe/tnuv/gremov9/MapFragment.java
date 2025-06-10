package si.uni_lj.fe.tnuv.gremov9;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private SharedViewModelDate sharedViewModelDate;
    private List<Event> fetchedEvents = new ArrayList<>();
    private boolean mapReady = false;
    private boolean eventsLoaded = false;
    private BitmapDescriptor customMarkerIcon;

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        getMapAsync(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharedViewModelDate = new ViewModelProvider(requireActivity()).get(SharedViewModelDate.class);

        // Load the custom icon only once
        loadCustomMarkerIcon(requireContext());

        new Thread(() -> {
            List<Event> events = new EventRssParser().fetchEvents();

            for (Event event : events) {
                GeocodingHelper.geocodeEventLocation(requireContext(), event);
            }

            requireActivity().runOnUiThread(() -> {
                fetchedEvents = events;
                eventsLoaded = true;
                updateMapMarkersIfReady();
            });
        }).start();

        sharedViewModelDate.getSelectedDate().observe(getViewLifecycleOwner(), selectedDate -> {
            updateMapMarkersIfReady();
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        mapReady = true;

        LatLng ljubljana = new LatLng(46.0569, 14.5058);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ljubljana, 13f));

        updateMapMarkersIfReady();
    }

    private void updateMapMarkersIfReady() {
        if (!mapReady || !eventsLoaded) return;

        googleMap.clear();
        Date selectedDate = sharedViewModelDate.getSelectedDate().getValue();

        for (Event event : fetchedEvents) {
            if (!event.hasCoordinates()) continue;

            if (selectedDate != null && event.getDate() != null) {
                try {
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                    Date eventDate = inputFormat.parse(event.getDate());

                    SimpleDateFormat dayOnly = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                    if (!dayOnly.format(eventDate).equals(dayOnly.format(selectedDate))) {
                        continue;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }
            }

            LatLng location = new LatLng(event.getLatitude(), event.getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(event.getTitle())
                    .icon(customMarkerIcon));
        }
    }

    private void loadCustomMarkerIcon(Context context) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gremo_location);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 80, 80, false);
        customMarkerIcon = BitmapDescriptorFactory.fromBitmap(scaledBitmap);
    }
}
