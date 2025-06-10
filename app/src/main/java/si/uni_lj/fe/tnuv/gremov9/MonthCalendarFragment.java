package si.uni_lj.fe.tnuv.gremov9;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MonthCalendarFragment extends Fragment {

    private CalendarView calendarView;
    private RecyclerView recyclerView;
    private List<Event> allEvents;
    private EventAdapter adapter;

    private SharedViewModelDate sharedViewModelDate;

    public MonthCalendarFragment() {}

    public static MonthCalendarFragment newInstance() {
        return new MonthCalendarFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar_month, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarView = view.findViewById(R.id.calendarView);
        recyclerView = view.findViewById(R.id.recyclerViewMonthEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        sharedViewModelDate = new ViewModelProvider(requireActivity()).get(SharedViewModelDate.class);

        // 1. Load all events in the background
        new Thread(() -> {
            EventRssParser parser = new EventRssParser();
            allEvents = parser.fetchEvents(); // load once

            requireActivity().runOnUiThread(() -> {
                // Get selected date from ViewModel if exists, otherwise use today
                Date selectedDate = sharedViewModelDate.getSelectedDate().getValue();
                if (selectedDate == null) {
                    selectedDate = new Date(); // default to today
                }

                // Set calendar view to this date
                calendarView.setDate(selectedDate.getTime(), false, true);

                // Show events for selected date
                showEventsForDate(selectedDate);
            });
        }).start();

        // 2. Respond to date changes
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            Calendar selectedCal = Calendar.getInstance();
            selectedCal.set(year, month, dayOfMonth);
            Date selectedDate = selectedCal.getTime();

            sharedViewModelDate.setSelectedDate(selectedDate); // Save the selected date
            showEventsForDate(selectedDate);
        });
    }


    private void showEventsForDate(Date date) {
        if (allEvents == null) return;

        // Format the selected date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String selectedDateStr = sdf.format(date);

        List<Event> eventsForDay = allEvents.stream()
                .filter(event -> {
                    try {
                        // Parse the event's date string to Date
                        Date eventDate = sdf.parse(event.getDate()); // assuming getStartDate() returns String
                        String eventDateStr = sdf.format(eventDate);
                        return selectedDateStr.equals(eventDateStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false; // skip event if date parsing fails
                    }
                })
                .collect(Collectors.toList());

        adapter = new EventAdapter(getContext(), eventsForDay);
        recyclerView.setAdapter(adapter);
    }

}
