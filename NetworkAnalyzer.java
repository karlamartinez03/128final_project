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

    public HashMap<User, Integer> calculateShortestPaths(User start) {
        HashMap<User, Integer> distances = new HashMap<>();
        PriorityQueue<Map.Entry<User, Integer>> pq = new PriorityQueue<>(Map.Entry.comparingByValue());
        Set<User> visited = new HashSet<>();

        for (User user : network.getUsers()) {
            distances.put(user, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        pq.offer(Map.entry(start, 0));

        while (!pq.isEmpty()) {
            User current = pq.poll().getKey();
            if (!visited.add(current)) continue;

            for (Connection conn : network.getConnections(current)) {
                User neighbor = conn.getUser2();
                int newDist = distances.get(current) + conn.getWeight();
                if (!visited.contains(neighbor) && newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    pq.offer(Map.entry(neighbor, newDist));
                }
            }
        }

        return distances;
    }
}

