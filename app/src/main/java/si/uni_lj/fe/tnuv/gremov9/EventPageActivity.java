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
import com.google.android.material.imageview.ShapeableImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EventPageActivity extends AppCompatActivity {

    private TextView titleEditText;
    private ShapeableImageView imageViewEvent;
    private TextView editTextDate;
    private TextView editTextDate2;
    private TextView locationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);

        imageViewEvent = findViewById(R.id.imageView2);
        titleEditText = findViewById(R.id.editTextText);
        //editTextDate = findViewById(R.id.editTextDate);
        editTextDate2 = findViewById(R.id.editTextDate2);
        locationEditText = findViewById(R.id.editTextDate3);

        Intent intent = getIntent();
        String title = intent.getStringExtra("event_title");
        String rawDate = intent.getStringExtra("event_date");
        String imageUrl = intent.getStringExtra("event_image_url");
        String description = intent.getStringExtra("event_description");
        String location = intent.getStringExtra("event_location");

        // Set title
        titleEditText.setText(title);

        // Load image with Glide
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.mgl) // fallback image from your XML
                .error(R.drawable.mgl)       // error fallback too
                .into(imageViewEvent);

        // Set the date and dateTime
        if (rawDate != null && !rawDate.isEmpty()) {
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault());
                Date date = inputFormat.parse(rawDate);

                if (date != null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd. MM. yyyy", Locale.getDefault());
                    String formattedDate = dateFormat.format(date);

                    // Check if time is exactly 00:00
                    SimpleDateFormat hourFormat = new SimpleDateFormat("HH", Locale.getDefault());
                    SimpleDateFormat minuteFormat = new SimpleDateFormat("mm", Locale.getDefault());
                    int hour = Integer.parseInt(hourFormat.format(date));
                    int minute = Integer.parseInt(minuteFormat.format(date));

                    editTextDate2.setText(formattedDate);

                    if (hour == 0 && minute == 0) {
                        // Time is 00:00, so only show date in the second field as well
                        editTextDate2.setText(formattedDate);
                    } else {
                        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd. MM. yyyy, HH:mm", Locale.getDefault());
                        String formattedDateTime = dateTimeFormat.format(date);
                        editTextDate2.setText(formattedDateTime);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
                editTextDate.setText(rawDate);
                editTextDate2.setText(rawDate);
            }
        } else {
            editTextDate.setText("Date not available");
            editTextDate2.setText("Date not available");
        }

        locationEditText.setText(location);

        // Setup go back button
        ImageButton goBackButton = findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(v -> finish());

        // Add this to fetch the event link from the intent
        String eventLink = intent.getStringExtra("event_link");

        // Set up website button
        Button websiteButton = findViewById(R.id.websiteButton);
        if (eventLink != null && !eventLink.isEmpty()) {
            websiteButton.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(eventLink));
                startActivity(browserIntent);
            });
        } else {
            websiteButton.setVisibility(View.GONE); // Hide if no link is available
        }
    }
}
