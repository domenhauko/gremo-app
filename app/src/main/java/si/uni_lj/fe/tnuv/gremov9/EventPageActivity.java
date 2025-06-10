package si.uni_lj.fe.tnuv.gremov9;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.imageview.ShapeableImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EventPageActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView titleEditText;
    private ShapeableImageView imageViewEvent;
    private TextView editTextDate2;
    private TextView locationEditText;
    private String title;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);

        imageViewEvent = findViewById(R.id.imageView2);
        titleEditText = findViewById(R.id.editTextText);
        editTextDate2 = findViewById(R.id.editTextDate2);
        locationEditText = findViewById(R.id.editTextDate3);

        Intent intent = getIntent();
        title = intent.getStringExtra("event_title");
        String rawDate = intent.getStringExtra("event_date");
        String imageUrl = intent.getStringExtra("event_image_url");
        String description = intent.getStringExtra("event_description");
        location = intent.getStringExtra("event_location");

        // Set title
        titleEditText.setText(title);

        // Load image with Glide
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.mgl)
                .error(R.drawable.mgl)
                .into(imageViewEvent);

        // Set the date and dateTime
        if (rawDate != null && !rawDate.isEmpty()) {
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault());
                Date date = inputFormat.parse(rawDate);

                if (date != null) {
                    SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd. MM. yyyy, HH:mm", Locale.getDefault());
                    String formattedDateTime = dateTimeFormat.format(date);

                    editTextDate2.setText(formattedDateTime);
                }
            } catch (ParseException e) {
                e.printStackTrace();
                editTextDate2.setText(rawDate);
            }
        } else {
            editTextDate2.setText("Date not available");
        }

        locationEditText.setText(location);

        // Setup go back button
        ImageButton goBackButton = findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(v -> finish());

        // Handle event link
        String eventLink = intent.getStringExtra("event_link");
        Button websiteButton = findViewById(R.id.websiteButton);
        if (eventLink != null && !eventLink.isEmpty()) {
            websiteButton.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(eventLink));
                startActivity(browserIntent);
            });
        } else {
            websiteButton.setVisibility(View.GONE);
        }

        // Load and prepare map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView2);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (location != null && !location.isEmpty()) {
            GeocodingHelper.geocodeLocation(this, location, latLng -> {
                if (latLng != null) {
                    googleMap.addMarker(new MarkerOptions().position(latLng).title(title));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
                }
            });
        }
    }
}
