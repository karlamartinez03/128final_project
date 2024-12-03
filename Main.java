import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Create users
        User user1 = new User("1", "Alice");
        User user2 = new User("2", "Bob");
        User user3 = new User("3", "Charlie");

        // Create social network
        SocialNetwork network = new SocialNetwork();
        network.addUser(user1);
        network.addUser(user2);
        network.addUser(user3);

        // Add connections between users
        network.addConnection(user1, user2, 5);
        network.addConnection(user2, user3, 3);
        network.addConnection(user1, user3, 1);

        // Community detection
        CommunityDetector detector = new CommunityDetector(network.getUsers());
        detector.union(user1, user2); // Alice and Bob are in the same community
        detector.union(user2, user3); // Bob and Charlie are also in the same community

        // Show communities
        System.out.println("Communities: " + detector.getCommunities());

        // Network analysis
        NetworkAnalyzer analyzer = new NetworkAnalyzer(network);
        System.out.println("Degree Centrality: " + analyzer.calculateDegreeCentrality());
        System.out.println("Shortest Paths from Alice: " + analyzer.calculateShortestPaths(user1));

        // Create an instance of GraphVisualizer
        GraphVisualizer visualizer = new GraphVisualizer();

        // Display the network in the console (or use the graphical method if implemented)
        visualizer.displayNetwork(network);

        // Optionally, visualize graphically (if you implement graphical visualization)
        visualizer.visualizeGraphically(network);
    }
}


