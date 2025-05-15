package si.uni_lj.fe.tnuv.gremov9;

import android.os.Bundle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import si.uni_lj.fe.tnuv.gremov9.databinding.ActivityEventPageBinding;

public class EventPageActivity extends AppCompatActivity {

private ActivityEventPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);
    }
}