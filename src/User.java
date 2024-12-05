import java.util.HashMap;
import java.util.Map;

public class User {
     private String id;
    private String name;
    private Map<String, String> metadata; // e.g., "location" -> "New York"

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.metadata = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addMetadata(String key, String value) {
        metadata.put(key, value);
    }

    public String getMetadata(String key) {
        return metadata.getOrDefault(key, "Unknown");
    }
}
