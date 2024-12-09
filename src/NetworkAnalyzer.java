import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

    public List<User> getLeaderboard() {
        HashMap<User, Integer> centrality = calculateDegreeCentrality();

        for (Map.Entry<User, Integer> entry : centrality.entrySet()) {
            entry.getKey().setConnectionCount(entry.getValue());
        }

        // sort the users based on their connection count
        List<User> users = new ArrayList<>(centrality.keySet());
        Collections.sort(users);

        // handle edge cases (ties)

        List<User> topUsers = new ArrayList<>();
        int rank = 1;
        int lastCount = -1;

        for (User user : users) {
            if(rank > 5 && user.getConnectionCount() != lastCount) {
                break;
            }
            topUsers.add(user);
            lastCount = user.getConnectionCount();
            rank++;
        }

        return topUsers;
    }

    public void displayLeaderboard() {
        PriorityQueue<User> pq = new PriorityQueue<>(); // Max-heap due to `Comparable` in User class

        // calculate degree centrality and populate priority queue
        for (User user : network.getUsers()) {
            int connectionCount = network.getConnections(user).size();
            user.setConnectionCount(connectionCount); 
            pq.offer(user);
        }

        // display the top users
        System.out.println("Leaderboard (Top Users by Connections):");
        int rank = 0;
        int lastCount = -1;
        int displayedCount = 0;
        // List<User> ties = new ArrayList<>();

        while (!pq.isEmpty() && displayedCount < 5) {
            User user = pq.poll();

            // // Handle ties
            // if (lastCount == -1 || user.getConnectionCount() == lastCount || rank <= 5) {
            //     ties.add(user);
            //     lastCount = user.getConnectionCount();
            // } else {
            //     break;
            // }

            // rank++;
            if (user.getConnectionCount() != lastCount) {
                rank = displayedCount + 1; 
                lastCount = user.getConnectionCount(); 
            }
            System.out.printf("#%d: %s%n", rank, user);
            displayedCount++;
        }

        // Print all tied users in the top ranks
    
    
    }
    
}

