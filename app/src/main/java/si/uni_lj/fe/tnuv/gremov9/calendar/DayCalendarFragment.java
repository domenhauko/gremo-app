package si.uni_lj.fe.tnuv.gremov9.calendar;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import si.uni_lj.fe.tnuv.gremov9.MainActivity;
import si.uni_lj.fe.tnuv.gremov9.MapsActivity;
import si.uni_lj.fe.tnuv.gremov9.R;

public class DayCalendarFragment extends Fragment {

    public DayCalendarFragment() {
        // Required empty public constructor
    }

    public static DayCalendarFragment newInstance() {
        return new DayCalendarFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar_day, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Calendar button
        Button calendarButton = view.findViewById(R.id.calendarButton);
        calendarButton.setOnClickListener(v -> {
            // Already in calendar view, optionally show toast or do nothing
        });

        // Map button
        Button mapButton = view.findViewById(R.id.mapButton);
        mapButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MapsActivity.class);
            startActivity(intent);
        });

        // GREMO home button
        ImageButton gremoHomeButton = view.findViewById(R.id.gremoHomeButton);
        gremoHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });

        // Month button (to switch to month view)
        Button monthButton = view.findViewById(R.id.monthButton);
        monthButton.setOnClickListener(v -> {
            // Replace this with actual fragment switch to MonthCalendarFragment
            // Example (if you're using FragmentManager):
            // requireActivity().getSupportFragmentManager().beginTransaction()
            //     .replace(R.id.fragment_container, new MonthCalendarFragment())
            //     .addToBackStack(null)
            //     .commit();
        });

        // Day button (already here, optional toast or state indication)
        Button dayButton = view.findViewById(R.id.dayButton);
        dayButton.setOnClickListener(v -> {
            // Optionally show toast or ignore
        });

        // Menu button
        Button menuButton = view.findViewById(R.id.menuButton);
        menuButton.setOnClickListener(v -> {
            // You can add functionality here (e.g., show a drawer, popup menu, etc.)
        });

        // TabLayout is present and initialized in the layout, no need to bind if static
        // If dynamic behavior is needed, you can bind and add listeners
    }
}
