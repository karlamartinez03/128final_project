import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
     public static void main(String[] args) {
        String csvFile = "student-dataset.csv"; // Path to the dataset
        SocialNetwork network = new SocialNetwork();


        // Step 1: Load data from the CSV
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isHeader = true;
        
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header
                }
        
                String[] values = line.split(",");

                int id1 = Integer.parseInt(values[0].trim());
                String name1 = values[1].trim();

                int id2 = Integer.parseInt(values[8].trim()); //DOES 15 ACTUALLY WORK, WELL IT SHOULD NOT
                String name2 = values[3].trim();

                double weight = Double.parseDouble(values[4].trim()); //CHANGE SO THAT IS NOT BY LATITUDE BUT BY DIFFERENT CHARARTSIC
        
                User user1 = network.getUsers().stream()
                        .filter(u -> u.getId() == id1)
                        .findFirst()
                        .orElseGet(() -> {
                            User newUser = new User(id1, name1, name2, name2, weight, weight, name2, name2, id2, weight, weight, weight, weight, id2, id2, id2);
                            network.addUser(newUser);
                            return newUser;
                        });
        
                User user2 = network.getUsers().stream()
                        .filter(u -> u.getId() == id2)
                        .findFirst()
                        .orElseGet(() -> {
                            User newUser = new User(id2, name2, name2, name2, weight, weight, name2, name2, id2, weight, weight, weight, weight, id2, id2, id2);
                            network.addUser(newUser);
                            return newUser;
                        });
        
                network.addConnection(user1, user2, weight); // Add connection with double weight
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing a number: " + e.getMessage());
        }
        

        // Step 4: Perform analysis and visualization
        CommunityDetector detector = new CommunityDetector(network.getUsers());
        for (User user : network.getUsers()) {
            for (Connection connection : network.getConnections(user)) {
                detector.union(connection.getUser1(), connection.getUser2());
            }
        }

        System.out.println("Communities: " + detector.getCommunities());

        NetworkAnalyzer analyzer = new NetworkAnalyzer(network);
        System.out.println("Degree Centrality: " + analyzer.calculateDegreeCentrality());

        // Assuming the first user from the CSV is the start point for shortest paths
        if (!network.getUsers().isEmpty()) {
            User startUser = network.getUsers().iterator().next();
            System.out.println("Shortest Paths from " + startUser.getName() + ": " +
                    analyzer.calculateShortestPaths(startUser));
        }

        GraphVisualizer visualizer = new GraphVisualizer();
        visualizer.visualizeGraphically(network);

    }
}





