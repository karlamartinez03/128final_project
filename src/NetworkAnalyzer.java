import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class NetworkAnalyzer {
    private SocialNetwork network;

    public NetworkAnalyzer(SocialNetwork network) {
        this.network = network;
    }

    public HashMap<User, Integer> calculateDegreeCentrality() {
        HashMap<User, Integer> centrality = new HashMap<>();
        for (User user : network.getUsers()) {
            centrality.put(user, network.getConnections(user).size());
        }
        return centrality;
    }

    public HashMap<User, Double> calculateShortestPaths(User start) {
        HashMap<User, Double> distances = new HashMap<>();
        PriorityQueue<Map.Entry<User, Double>> pq = new PriorityQueue<>(Map.Entry.comparingByValue());
        Set<User> visited = new HashSet<>();
    
        for (User user : network.getUsers()) {
            distances.put(user, Double.MAX_VALUE); // Initialize with infinity
        }
        distances.put(start, 0.0); // Distance to start is 0
        pq.offer(Map.entry(start, 0.0)); // Add start node to the priority queue
    
        while (!pq.isEmpty()) {
            User current = pq.poll().getKey(); // Get the user with the smallest distance
            if (!visited.add(current)) continue;
    
            for (Connection conn : network.getConnections(current)) {
                User neighbor = conn.getUser2();
                double newDist = distances.get(current) + conn.getWeight(); // Use double weights
                if (!visited.contains(neighbor) && newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    pq.offer(Map.entry(neighbor, newDist)); // Update the queue
                }
            }
        }
    
        return distances;
    }
    
}

