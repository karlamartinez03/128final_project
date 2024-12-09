import java.util.HashMap;
import java.util.Map;

public class User implements Comparable<User>{
    private String id;
    private String name;
    private Map<String, String> metadata; // e.g., "location" -> "New York"
    private int connectionCount;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.metadata = new HashMap<>();
        this.connectionCount = 0;
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

    public void setConnectionCount(int count) {
        this.connectionCount = count;
    }

    public int getConnectionCount() {
        return connectionCount;
    }

    @Override
    public int compareTo(User other) {
        return Integer.compare(other.connectionCount, this.connectionCount);
    }

    @Override
    public String toString() {
        return String.format("%s (Connections: %d)", name, connectionCount);
    }

}
