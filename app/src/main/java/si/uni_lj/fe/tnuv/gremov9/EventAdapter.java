package si.uni_lj.fe.tnuv.gremov9;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context context;
    private List<Event> eventList;

    public EventAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.textViewTitle.setText(event.getTitle());
        holder.textViewDate.setText(formatDate(event.getDate()));
        holder.textViewLocation.setText(event.getLocation());
        holder.textViewDescription.setText(event.getDescription());

        // Load image using Glide (you can use Picasso or Coil instead)
        Glide.with(context)
                .load(event.getImageUrl())
                .placeholder(R.drawable.gremo_icon) // fallback image
                .into(holder.imageViewEvent);

        // Set click listener on the whole item view (card)
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EventPageActivity.class);
            intent.putExtra("event_title", event.getTitle());
            intent.putExtra("event_date", event.getDate()); // assuming event.getDate() returns Date
            intent.putExtra("event_description", event.getDescription());
            intent.putExtra("event_location", event.getLocation());
            intent.putExtra("event_image_url", event.getImageUrl()); // optional, if you want to pass image

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewDate, textViewLocation, textViewDescription;
        ImageView imageViewEvent;

        public EventViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            imageViewEvent = itemView.findViewById(R.id.imageViewEvent);
        }
    }

    public static String formatDate(String rawDate) {
        if (rawDate == null || rawDate.isEmpty()) {
            return "Date not available";
        }

        String[] possibleFormats = {
                "yyyyMMdd'T'HHmmss'Z'",          // e.g., 20250606T120000Z
                "yyyy-MM-dd'T'HH:mm:ssXXX",      // with timezone
                "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",  // with milliseconds and timezone
                "yyyy-MM-dd'T'HH:mm:ss"          // without timezone
        };

        for (String format : possibleFormats) {
            try {
                SimpleDateFormat originalFormat = new SimpleDateFormat(format, Locale.getDefault());
                originalFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date date = originalFormat.parse(rawDate);

                SimpleDateFormat desiredFormat = new SimpleDateFormat("d. M. yyyy", Locale.getDefault());
                desiredFormat.setTimeZone(TimeZone.getDefault()); // Use device timezone for display
                return desiredFormat.format(date);
            } catch (ParseException e) {
                // Try next format
            }
        }

        return rawDate;
    }
}

