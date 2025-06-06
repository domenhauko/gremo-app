package si.uni_lj.fe.tnuv.gremov9;

public class Event {
    private String title;
    private String location;
    private String date;
    private String imageUrl;
    private String description;

    public Event(String title, String location, String date, String imageUrl, String description) {
        this.title = title;
        this.location = location;
        this.date = date;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    // Setters (optional, if you need to modify the data)
    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

