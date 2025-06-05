package si.uni_lj.fe.tnuv.gremov9.EventPage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import si.uni_lj.fe.tnuv.gremov9.R;

public class EventPageFragment extends Fragment {
    public EventPageFragment() {
        // Required empty constructor
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_page, container, false);
    }
}
