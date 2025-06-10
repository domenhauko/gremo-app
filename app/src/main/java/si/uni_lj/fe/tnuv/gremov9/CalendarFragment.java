package si.uni_lj.fe.tnuv.gremov9;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CalendarFragment extends Fragment {

    public CalendarFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        Button monthButton = view.findViewById(R.id.buttonMonth);
        Button dayButton = view.findViewById(R.id.buttonDay);

        getChildFragmentManager().beginTransaction()
                .replace(R.id.calendarNestedFragment, new DayCalendarFragment())
                .commit();
        updateActiveButton(dayButton, monthButton);

        // Day button
        dayButton.setOnClickListener(v -> {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.calendarNestedFragment, new DayCalendarFragment())
                    .commit();
            updateActiveButton(dayButton, monthButton);
        });

        // Month button
        monthButton.setOnClickListener(v -> {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.calendarNestedFragment, new MonthCalendarFragment())
                    .commit();
            updateActiveButton(monthButton, dayButton);
        });

        return view;
    }
    private void loadNestedFragment(Fragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.calendarNestedFragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private void updateActiveButton(Button active, Button inactive) {
        // Use requireContext() to get the Context inside a Fragment
        active.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.pink));
        inactive.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.dark_blue));
    }

}