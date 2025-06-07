package si.uni_lj.fe.tnuv.gremov9;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

        setupTabs();

        new Thread(() -> {
            EventRssParser parser = new EventRssParser();
            List<Event> events = parser.fetchEvents();
            allEvents = events;

            requireActivity().runOnUiThread(() -> {
                filterAndShowEvents(0); // default: first tab
            });
        }).start();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                filterAndShowEvents(tab.getPosition());
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void setupTabs() {
        SimpleDateFormat labelFormat = new SimpleDateFormat("EEE dd.MM", Locale.getDefault());
        SimpleDateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < 3; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(labelFormat.format(calendar.getTime())));
            fullDates.add(fullDateFormat.format(calendar.getTime()));
            calendar.add(Calendar.DATE, 1);
        }
    }

    private void filterAndShowEvents(int tabIndex) {
        String selectedDate = fullDates.get(tabIndex);

        List<Event> filtered = new ArrayList<>();
        for (Event event : allEvents) {
            if (event.getDate() != null && event.getDate().startsWith(selectedDate)) {
                filtered.add(event);
            }
        }

        adapter = new EventAdapter(getContext(), filtered);
        recyclerView.setAdapter(adapter);
    }
}
