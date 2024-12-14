import java.util.*;

public class SocialNetwork {
    private List<User> users; 
    private Map<User, List<Connection>> connections;

    public SocialNetwork(){
        users = new ArrayList<>();
        connections = new HashMap<>();
    }

    public void addUser(User user) {
        users.add(user);
        connections.putIfAbsent(user, new ArrayList<>());
    }

    public void addConnection(User user1, User user2, double weight) {
        connections.get(user1).add(new Connection(user1, user2, weight));
        connections.get(user2).add(new Connection(user2, user1, weight)); 
    }

    public List<Connection> getConnections(User user) {
        return connections.getOrDefault(user, Collections.emptyList());
    }

    public List<User> getUsers() {
        return users;
    }
}
