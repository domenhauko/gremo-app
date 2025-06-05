package si.uni_lj.fe.tnuv.gremov9.calendar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import org.jetbrains.annotations.Nullable;

import si.uni_lj.fe.tnuv.gremov9.Map.MapFragment;
import si.uni_lj.fe.tnuv.gremov9.R;

public class CalendarFragment extends Fragment {

    public CalendarFragment() {
        // Required empty public constructor
    }

    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Button dayButton = view.findViewById(R.id.dayButton);
        Button monthButton = view.findViewById(R.id.monthButton);

        if (savedInstanceState == null) {
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentContainer, new CalendarFragment()) // create this!
                    .commit();
        }

        /*
        calendarButton.setOnClickListener(v -> {
            // You are already on the calendar screen; this can be empty or show a toast
        });
        */


        // dan button = klik na Dan
        dayButton.setOnClickListener(v -> {
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentContainer, new DayCalendarFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }


}