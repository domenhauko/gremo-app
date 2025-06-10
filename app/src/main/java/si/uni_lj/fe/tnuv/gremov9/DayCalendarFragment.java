package si.uni_lj.fe.tnuv.gremov9;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DayCalendarFragment extends Fragment {

    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private List<Event> allEvents = new ArrayList<>();
    private List<String> fullDates = new ArrayList<>();

    private SharedViewModelDate sharedViewModelDate;
    private SimpleDateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public DayCalendarFragment() {
        // Required empty public constructor
    }

    public static DayCalendarFragment newInstance() {
        return new DayCalendarFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar_day, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = view.findViewById(R.id.tabLayout);
        recyclerView = view.findViewById(R.id.recyclerViewEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        sharedViewModelDate = new ViewModelProvider(requireActivity()).get(SharedViewModelDate.class);

        // Load all events in background thread
        new Thread(() -> {
            EventRssParser parser = new EventRssParser();
            allEvents = parser.fetchEvents();

            requireActivity().runOnUiThread(() -> {
                // Initially setup tabs and show events for selectedDate or today if none
                Date initialDate = sharedViewModelDate.getSelectedDate().getValue();
                if (initialDate == null) {
                    initialDate = new Date();
                    sharedViewModelDate.setSelectedDate(initialDate);
                }
                setupTabs(initialDate);
                selectTabForDate(initialDate);
                filterAndShowEvents(fullDateFormat.format(initialDate));
            });
        }).start();

        // Respond to tab selection changes: update sharedViewModelDate with new date
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position < fullDates.size()) {
                    try {
                        Date date = fullDateFormat.parse(fullDates.get(position));
                        if (date != null) {
                            sharedViewModelDate.setSelectedDate(date);  // update shared date
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        // Observe sharedViewModelDate changes (from MonthFragment or other source)
        sharedViewModelDate.getSelectedDate().observe(getViewLifecycleOwner(), selectedDate -> {
            if (selectedDate != null) {
                updateTabsStartingFrom(selectedDate);
                selectTabForDate(selectedDate);  // Select the correct tab
                filterAndShowEvents(fullDateFormat.format(selectedDate));
            }
        });
    }

    /**
     * Selects the tab in tabLayout that matches the given date.
     */
    private void selectTabForDate(Date date) {
        String dateStr = fullDateFormat.format(date);
        int index = fullDates.indexOf(dateStr);
        if (index != -1 && tabLayout.getSelectedTabPosition() != index) {
            TabLayout.Tab tab = tabLayout.getTabAt(index);
            if (tab != null) {
                tab.select();
            }
        }
    }

    private void setupTabs(Date baseDate) {
        tabLayout.removeAllTabs();
        fullDates.clear();

        SimpleDateFormat labelFormat = new SimpleDateFormat("EEE dd.MM", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(baseDate);

        for (int i = 0; i < 3; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(labelFormat.format(calendar.getTime())));
            fullDates.add(fullDateFormat.format(calendar.getTime()));
            calendar.add(Calendar.DATE, 1);
        }
    }

    private void updateTabsStartingFrom(Date selectedDate) {
        setupTabs(selectedDate);
    }

    private void filterAndShowEvents(String selectedDateStr) {
        List<Event> filtered = new ArrayList<>();
        for (Event event : allEvents) {
            if (event.getDate() != null && event.getDate().startsWith(selectedDateStr)) {
                filtered.add(event);
            }
        }

        adapter = new EventAdapter(getContext(), filtered);
        recyclerView.setAdapter(adapter);
    }
}
