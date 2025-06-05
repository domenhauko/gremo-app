package si.uni_lj.fe.tnuv.gremov9.calendar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import si.uni_lj.fe.tnuv.gremov9.Map.MapFragment;
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

        Button calendarButton = view.findViewById(R.id.calendarButton);
        Button mapButton = view.findViewById(R.id.mapButton);
        ImageButton gremoHomeButton = view.findViewById(R.id.gremoHomeButton);
        Button dayButton = view.findViewById(R.id.dayButton);
        Button monthButton = view.findViewById(R.id.monthButton);
        ImageButton menuButton = view.findViewById(R.id.menuButton);

        if (savedInstanceState == null) {
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentContainer, new DayCalendarFragment()) // create this!
                    .commit();
        }

        /*
        calendarButton.setOnClickListener(v -> {
            // Already in calendar view, optionally show toast or do nothing
        });
        */

        // klik na Zemljevid
        mapButton.setOnClickListener(v -> {
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentContainer, new MapFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // klik na logo/GREMO
        gremoHomeButton.setOnClickListener(v -> {
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentContainer, new DayCalendarFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // klik na Mesec
        monthButton.setOnClickListener(v -> {
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentContainer, new CalendarFragment())
                    .addToBackStack(null)
                    .commit();
        });

        menuButton.setOnClickListener(v -> {
            // You can add functionality here (e.g., show a drawer, popup menu, etc.)
        });

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
