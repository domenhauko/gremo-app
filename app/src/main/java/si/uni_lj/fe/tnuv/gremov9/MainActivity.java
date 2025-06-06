package si.uni_lj.fe.tnuv.gremov9;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button calendarButton;
    private Button mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        calendarButton = findViewById(R.id.calendarButton);
        mapButton = findViewById(R.id.mapButton);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new CalendarFragment())
                .commit();
        updateActiveButton(calendarButton, mapButton);

        //Map button
        mapButton.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MapFragment())
                    .commit();
            updateActiveButton(mapButton, calendarButton);
        });


        // Calendar button
        calendarButton.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new CalendarFragment())
                    .commit();
            updateActiveButton(calendarButton, mapButton);
        });


        //GREMO home button
        ImageButton gremoHomeButton = findViewById(R.id.gremoHomeButton);
        gremoHomeButton.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "You are already on the home screen", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateActiveButton(Button active, Button inactive) {
        active.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.pink));
        inactive.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.dark_blue));
    }


}