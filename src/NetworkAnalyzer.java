import java.util.*;
import java.util.List;
import java.awt.*;
import edu.macalester.graphics.*;
import edu.macalester.graphics.Rectangle;

//Data Structure used: Priority queues, Hash Map, 

public class NetworkAnalyzer {
    private SocialNetwork socialNetwork;

    public NetworkAnalyzer(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    HashMap<User, Integer> calculateDegreeCentrality() {
        HashMap<User, Integer> centrality = new HashMap<>();
        for (User user : socialNetwork.getUsers()) {
            centrality.put(user, socialNetwork.getConnections(user).size());
        }
        return centrality;
    }

    public HashMap<User, Double> calculateShortestPaths(User start) {
        HashMap<User, Double> distances = new HashMap<>();
        PriorityQueue<Map.Entry<User, Double>> pq = new PriorityQueue<>(Map.Entry.comparingByValue());
        Set<User> visited = new HashSet<>();
    
        for (User user : socialNetwork.getUsers()) {
            distances.put(user, Double.MAX_VALUE); // Initialize with infinity
        }
        distances.put(start, 0.0); // Distance to start is 0
        pq.offer(Map.entry(start, 0.0)); // Add start node to the priority queue
    
        while (!pq.isEmpty()) {
            User current = pq.poll().getKey(); // Get the user with the smallest distance
            if (!visited.add(current)) continue;
    
            for (Connection conn : socialNetwork.getConnections(current)) {
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
        // Calculate degree centrality for each user
        HashMap<User, Integer> centrality = calculateDegreeCentrality();

        // Update connection count in each user object
        for (Map.Entry<User, Integer> entry : centrality.entrySet()) {
            entry.getKey().setConnectionCount(entry.getValue());
        }

        // Sort the users by connection count in descending order
        List<User> users = new ArrayList<>(centrality.keySet());
        users.sort(Comparator.comparingInt(User::getConnectionCount).reversed());

        // Create a list of top users with ties handled
        List<User> topUsers = new ArrayList<>();
        int rank = 1;
        int lastCount = -1;

        for (User user : users) {
            if (rank > 5 && user.getConnectionCount() != lastCount) {
                break;
            }
            topUsers.add(user);
            lastCount = user.getConnectionCount();
            rank++;
        }

        return topUsers;
    }

    public void displayLeaderboard(CanvasWindow canvas, int leaderWidth, int leaderHeight) {
        List<User> topUsers = getLeaderboard();

        // Display leaderboard background
        Rectangle leaderboardBackground = new Rectangle(10, 10, leaderWidth, leaderHeight);
        leaderboardBackground.setFillColor(new Color(0, 0, 0, 50)); // Semi-transparent black
        leaderboardBackground.setStrokeColor(Color.BLACK);
        canvas.add(leaderboardBackground);

        // Title text
        GraphicsText title = new GraphicsText("Leaderboard", 15, 30);
        title.setFontSize(14);
        title.setFillColor(Color.WHITE);
        canvas.add(title);

        // Display the top users with ranks
        int yOffset = 50; // Starting y-offset for user entries
        int rank = 1;
        int lastConnectionCount = -1;

        for (User user : topUsers) {
            // Handle ties in ranking
            if (user.getConnectionCount() != lastConnectionCount) {
                lastConnectionCount = user.getConnectionCount();
            }

            // Construct leaderboard entry text
            String entryText = rank + ". " + user.getName() + " (" + user.getConnectionCount() + ")";
            GraphicsText userText = new GraphicsText(entryText, 20, yOffset);
            userText.setFontSize(14);
            userText.setFillColor(Color.WHITE);
            canvas.add(userText);

            yOffset += 25; // Increment y-offset for the next entry
            rank++;
        }
    }

}
