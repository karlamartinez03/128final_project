import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.*;


public class SocialNetwork {
    Map<Integer, User> users; // User ID -> User object
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


// public class SocialNetwork {
//     private final Map<User, Set<User>> connections = new HashMap<>();

//     public void addUser(User user) {
//         connections.putIfAbsent(user, new HashSet<>());
//     }

//     public void addConnection(User user1, User user2) {
//         if (!connections.containsKey(user1) || !connections.containsKey(user2)) {
//             throw new IllegalArgumentException("Both users must be in the network.");
//         }
//         connections.get(user1).add(user2);
//         connections.get(user2).add(user1); // Assuming bidirectional connections
//     }

//     public Set<User> getConnections(User user) {
//         return connections.getOrDefault(user, new HashSet<>());
//     }

//     public Set<User> getAllUsers() {
//         return connections.keySet();
//     }
// }

