import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        // Create a SocialNetwork instance
        SocialNetwork socialNetwork = new SocialNetwork();

        // Read users from the CSV file
        CSVReader csvReader = new CSVReader();
        HashMap<String, User> users = csvReader.parseUsers();

        // Add users to the social network
        for (User user : users.values()) {
            socialNetwork.addUser(user);
        }

        // Add connections between users (example connections)
        socialNetwork.addConnection(users.get("0"), users.get("1"));
        socialNetwork.addConnection(users.get("2"), users.get("3"));
        socialNetwork.addConnection(users.get("4"), users.get("5"));

        // Perform network analysis
        NetworkAnalyzer analyzer = new NetworkAnalyzer(socialNetwork);
        analyzer.calculateShortestPaths(users.get("0")); // Example: analyze from user ID 0

        // Display results
        analyzer.printShortestPaths();
    }
}



// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.IOException;

// public class Main {
//     public static void main(String[] args) {
//         String csvFile = "student-dataset.csv"; // Path to the dataset
//         SocialNetwork network = new SocialNetwork();


//         // Step 1: Load data from the CSV
//         try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
//             String line;
//             boolean isHeader = true;
        
//             while ((line = br.readLine()) != null) {
//                 if (isHeader) {
//                     isHeader = false;
//                     continue; // Skip header
//                 }
        
//                 String[] values = line.split(",");
//                 String id1 = values[0].trim();
//                 String name1 = values[1].trim();
//                 String id2 = values[2].trim();
//                 String name2 = values[3].trim();
//                 double weight = Double.parseDouble(values[4].trim()); //CHANGE SO THAT IS NOT BY LATITUDE BUT BY DIFFERENT CHARARTSIC
        
//                 User user1 = network.getUsers().stream()
//                         .filter(u -> u.getId().equals(id1))
//                         .findFirst()
//                         .orElseGet(() -> {
//                             User newUser = new User(id1, name1);
//                             network.addUser(newUser);
//                             return newUser;
//                         });
        
//                 User user2 = network.getUsers().stream()
//                         .filter(u -> u.getId().equals(id2))
//                         .findFirst()
//                         .orElseGet(() -> {
//                             User newUser = new User(id2, name2);
//                             network.addUser(newUser);
//                             return newUser;
//                         });
        
//                 network.addConnection(user1, user2, weight); // Add connection with double weight
//             }
//         } catch (IOException e) {
//             System.err.println("Error reading the CSV file: " + e.getMessage());
//         } catch (NumberFormatException e) {
//             System.err.println("Error parsing a number: " + e.getMessage());
//         }
        

//         // Step 4: Perform analysis and visualization
//         CommunityDetector detector = new CommunityDetector(network.getUsers());
//         for (User user : network.getUsers()) {
//             for (Connection connection : network.getConnections(user)) {
//                 detector.union(connection.getUser1(), connection.getUser2());
//             }
//         }

//         System.out.println("Communities: " + detector.getCommunities());

//         NetworkAnalyzer analyzer = new NetworkAnalyzer(network);
//         System.out.println("Degree Centrality: " + analyzer.calculateDegreeCentrality());

//         // Assuming the first user from the CSV is the start point for shortest paths
//         if (!network.getUsers().isEmpty()) {
//             User startUser = network.getUsers().iterator().next();
//             System.out.println("Shortest Paths from " + startUser.getName() + ": " +
//                     analyzer.calculateShortestPaths(startUser));
//         }

//         GraphVisualizer visualizer = new GraphVisualizer();
//         visualizer.visualizeGraphically(network);

//         analyzer.displayLeaderboard();

//     }
// }

// public class Main {
//     public static void main(String[] args) {
//         // Create users
//         User user1 = new User("1", "Alice");
//         User user2 = new User("2", "Bob");
//         User user3 = new User("3", "Charlie");

//         // Create social network
//         SocialNetwork network = new SocialNetwork();
//         network.addUser(user1);
//         network.addUser(user2);
//         network.addUser(user3);

//         // Add connections between users
//         network.addConnection(user1, user2, 5);
//         network.addConnection(user2, user3, 3);
//         network.addConnection(user1, user3, 1);

//         // Community detection
//         CommunityDetector detector = new CommunityDetector(network.getUsers());
//         detector.union(user1, user2); // Alice and Bob are in the same community
//         detector.union(user2, user3); // Bob and Charlie are also in the same community

//         // Show communities
//         System.out.println("Communities: " + detector.getCommunities());

//         // Network analysis
//         NetworkAnalyzer analyzer = new NetworkAnalyzer(network);
//         System.out.println("Degree Centrality: " + analyzer.calculateDegreeCentrality());
//         System.out.println("Shortest Paths from Alice: " + analyzer.calculateShortestPaths(user1));

//         // Create an instance of GraphVisualizer
//         GraphVisualizer visualizer = new GraphVisualizer();

//         // Display the network in the console (or use the graphical method if implemented)
//         visualizer.displayNetwork(network);

//         // Optionally, visualize graphically (if you implement graphical visualization)
//         visualizer.visualizeGraphically(network);
//     }
// }



