    package si.uni_lj.fe.tnuv.gremov9;

    import android.util.Xml;

    import org.xmlpull.v1.XmlPullParser;

    import java.io.InputStream;
    import java.net.HttpURLConnection;
    import java.net.URL;
    import java.util.ArrayList;
    import java.util.List;

    public class EventRssParser {

        public List<Event> fetchEvents() {
            List<Event> events = new ArrayList<>();
            try {
                URL url = new URL("https://dogodki.kulturnik.si/?format=rss");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                InputStream stream = conn.getInputStream();

                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
                parser.setInput(stream, null);

                Event currentEvent = null;
                int eventType = parser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String name;
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            name = parser.getName();
                            String namespace = parser.getNamespace();
                            if (name.equalsIgnoreCase("item")) {
                                currentEvent = new Event("", "", "", "", "");
                            } else if (currentEvent != null) {
                                switch (name) {
                                    case "title":
                                        currentEvent.setTitle(parser.nextText());
                                        break;
                                    case "description":
                                        currentEvent.setDescription(parser.nextText());
                                        break;
                                    case "dtstart":
                                    case "ical:dtstart":
                                        currentEvent.setDate(parser.nextText());
                                        break;
                                    case "location":
                                    case "ical:location":
                                        currentEvent.setLocationString(parser.nextText());
                                        break;
                                    case "enclosure":
                                        String imageUrl = parser.getAttributeValue(null, "url");
                                        currentEvent.setImageUrl(imageUrl);
                                        break;
                                    // Skip pubDate, category, and others
                                }
                            }
                            break;

                        case XmlPullParser.END_TAG:
                            name = parser.getName();
                            if (name.equalsIgnoreCase("item") && currentEvent != null) {
                                if (currentEvent.getDate() == null || currentEvent.getDate().isEmpty()) {
                                    currentEvent.setDate("Date not given"); // Fallback to empty string
                                }
                                if (currentEvent.getLocationString() == null || currentEvent.getLocationString().isEmpty()) {
                                    currentEvent.setLocationString("Location not given"); // Fallback to empty string
                                }
                                events.add(currentEvent);
                            }
                            break;
                    }
                    eventType = parser.next();
                }

                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return events;
        }
    }
