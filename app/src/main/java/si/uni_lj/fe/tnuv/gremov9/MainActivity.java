package si.uni_lj.fe.tnuv.gremov9;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import si.uni_lj.fe.tnuv.gremov9.EventPageActivity;
//import si.uni_lj.fe.tnuv.gremov9.eventPage.EventPageActivity;
import si.uni_lj.fe.tnuv.gremov9.MapFragment;
import si.uni_lj.fe.tnuv.gremov9.calendar.DayCalendarFragment;

public class MainActivity extends AppCompatActivity {

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

        // Calendar button
        Button calendarButton = findViewById(R.id.calendarButton);
        calendarButton.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new DayCalendarFragment())
                    .commit();
        });

        //Map button
        Button mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MapFragment())
                    .commit();
        });



        //GREMO home button
        ImageButton gremoHomeButton = findViewById(R.id.gremoHomeButton);
        gremoHomeButton.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "You are already on the home screen", Toast.LENGTH_SHORT).show();
        });


        //Začasen dogodek button
        Button eventButton = findViewById(R.id.eventButton);
        eventButton.setOnClickListener(v -> {
            Intent eventPage = new Intent(MainActivity.this, EventPageActivity.class);
            startActivity(eventPage);
            /*
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new EventPageActivity())
                    .commit();
        */
        });

    }
}