package si.uni_lj.fe.tnuv.gremov9;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeocodingHelper {

    public static void geocodeEventLocation(Context context, Event event) {
        if (event == null || event.getLocationString() == null || event.getLocationString().isEmpty()) return;

        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocationName(event.getLocationString(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                event.setCoordinates(address.getLatitude(), address.getLongitude());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 👇 NEW: for generic location lookup with a callback
    public interface GeocodingCallback {
        void onGeocoded(LatLng latLng);
    }

    public static void geocodeLocation(Context context, String location, GeocodingCallback callback) {
        if (location == null || location.isEmpty()) {
            callback.onGeocoded(null);
            return;
        }

        new Thread(() -> {
            try {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocationName(location, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    Address address = addresses.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                    // Run callback on the main thread
                    ((android.app.Activity) context).runOnUiThread(() -> callback.onGeocoded(latLng));
                } else {
                    ((android.app.Activity) context).runOnUiThread(() -> callback.onGeocoded(null));
                }
            } catch (IOException e) {
                e.printStackTrace();
                ((android.app.Activity) context).runOnUiThread(() -> callback.onGeocoded(null));
            }
        }).start();
    }
}
