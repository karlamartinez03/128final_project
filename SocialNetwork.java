import java.util.*;

public class SocialNetwork {
    private Map<String, User> users; // User ID -> User object
    private Map<User, List<Connection>> connections; // Adjacency list

    public SocialNetwork() {
        users = new HashMap<>();
        connections = new HashMap<>();
    }

    public void addUser(User user) {
        users.putIfAbsent(user.getId(), user);
        connections.putIfAbsent(user, new ArrayList<>());
    }

    public void addConnection(User user1, User user2, double weight) {
        Connection connection = new Connection(user1, user2, weight);
        connections.get(user1).add(connection);
        connections.get(user2).add(new Connection(user2, user1, weight)); // Symmetric
    }

    public List<Connection> getConnections(User user) {
        return connections.getOrDefault(user, new ArrayList<>());
    }

    public Set<User> getUsers() {
        return new HashSet<>(users.values());
    }
}

