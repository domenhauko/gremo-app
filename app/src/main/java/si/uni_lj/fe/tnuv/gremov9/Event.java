package si.uni_lj.fe.tnuv.gremov9;

public class Event {
    String title;
    String description;
    String date;
    String locationString; // The location string from RSS
    String imageUrl;
    String link;
    double latitude;
    double longitude;
    boolean hasCoordinates = false; // Flag to check if lat/lng are set

    // Constructor, getters, and setters
    public Event(String title, String description, String date, String locationString, String imageUrl, String link) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.locationString = locationString; // Store the original location string
        this.imageUrl = imageUrl;
        this.link = link;
    }

    // Add methods to set and check coordinates
    public void setCoordinates(double lat, double lng) {
        this.latitude = lat;
        this.longitude = lng;
        this.hasCoordinates = true;
    }

    public boolean hasCoordinates() {
        return hasCoordinates;
    }

    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getTitle() { return title; } // Ensure you have this
    public String getName() { return title; } // Or getName() if you prefer
    public String getDescription() { return description; }
    public String getLocationString() { return locationString; }
    public String getDate() { return date; }
    public String getImageUrl() { return imageUrl; }
    public String getLink() { return link; }



    // You might need to add setters if you parse different parts at different times
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setDate(String date) { this.date = date; }
    public void setLocationString(String locationString) { this.locationString = locationString; } // Renamed for clarity
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setLink(String link) { this.link = link; }
}