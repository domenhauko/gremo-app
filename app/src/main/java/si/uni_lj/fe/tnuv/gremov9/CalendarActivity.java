package si.uni_lj.fe.tnuv.gremov9;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calendar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Calendar button
        Button calendarButton = findViewById(R.id.calendarButton);
        calendarButton.setOnClickListener(v -> {
            Intent intent = new Intent(CalendarActivity.this, CalendarActivity.class);
            startActivity(intent);
        });

        //Map button
        Button mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(v -> {
            Intent intent = new Intent(CalendarActivity.this, MapsActivity.class);
            startActivity(intent);
        });

        //GREMO home button
        ImageButton gremoHomeButton = findViewById(R.id.gremoHomeButton);
        gremoHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(CalendarActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }
}