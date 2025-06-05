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
import si.uni_lj.fe.tnuv.gremov9.PrenosPodatkov;
import si.uni_lj.fe.tnuv.gremov9.R;

import android.widget.TextView;




public class DayCalendarFragment extends Fragment {


    // 2. Add this inside the class (below other fields, if any):
    private TextView textViewEvents;


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

        // Month button (to switch to month view)
        Button monthButton = view.findViewById(R.id.buttonMonth);
        monthButton.setOnClickListener(v -> {
            // Replace this with actual fragment switch to MonthCalendarFragment
            // Example (if you're using FragmentManager):
            // requireActivity().getSupportFragmentManager().beginTransaction()
            //     .replace(R.id.fragment_container, new MonthCalendarFragment())
            //     .addToBackStack(null)
            //     .commit();
        });

        // Day button (already here, optional toast or state indication)
        Button dayButton = view.findViewById(R.id.buttonDay);
        dayButton.setOnClickListener(v -> {
            // Optionally show toast or ignore
        });


        // TabLayout is present and initialized in the layout, no need to bind if static
        // If dynamic behavior is needed, you can bind and add listeners

        //povezava s PrenosPodatki
        //povežemo s textView predelom
        TextView textViewDayCalendar = view.findViewById(R.id.textViewDayCalendar);
        // fetch JSON in update UI
        String url = "https://novice.kulturnik.si/format/json";
        new Thread(() -> {
            // Use existing PrenosPodatkov class (pass getActivity() as context)
            PrenosPodatkov pp = new PrenosPodatkov(url, requireActivity());
            String rezultat = pp.prenesiPodatke(); // gets raw JSON or error message

            requireActivity().runOnUiThread(() -> {
                textViewDayCalendar.setText(rezultat); // Show result on screen
            });
        }).start();

    }
}
