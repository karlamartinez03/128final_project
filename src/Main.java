import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * Main method reading the csv files and calling other classes on the parsed data to compare users
 * and visualize the social network.
 */
public class Main {
    public static NetworkAnalyzer analyzer;

    public static void main(String[] args) throws FileNotFoundException, IOException {

        String csvFile = "student-dataset.csv";
        SocialNetwork network = new SocialNetwork();
        analyzer = new NetworkAnalyzer(network);


        // initialize Array Lists to store the csv data

        // List<String[]> csvData1 = new ArrayList<>();
                
        try (BufferedReader reader1 = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isHeader = true;
            while ((line = reader1.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                String nationality = data[2];
                String city = data[3];
                int age = Integer.parseInt(data[8]);
        
                // Create user and add to network
                User user = new User(id, name, nationality, city, age);
                network.addUser(user);
            }
        }
        
                                  
                    // iterate through the array of string to get the name and id of the user
                    List<User> users = network.getUsers();
            for (int i = 0; i < users.size(); i++) {
                for (int j = i + 1; j < users.size(); j++) {
                    User user1 = users.get(i);
                    User user2 = users.get(j);

                    double weight = analyzer.calculateWeight(user1, user2);
                    if (weight > 0) {
                        network.addConnection(user1, user2, weight);
                    }
                }
            }
        
            System.out.println("Network created with users and connections!");
        
            // handle edge cases
            // } catch (IOException e) {
            //     System.err.println("Error reading CSV file: " + e.getMessage());
            // } catch (NumberFormatException e) {
            //     System.err.println("Error parsing number: " + e.getMessage());
            // }

    
        Map<User, Double> pageRank = analyzer.calculatePageRank(15, 0.85); 

        System.out.println("PageRank Scores:");
        for (Map.Entry<User, Double> entry : pageRank.entrySet()) {

            System.out.printf("%s: %.4f%n", entry.getKey().getName(), entry.getValue());
        }

        System.out.println("PageRank Scores:");

        pageRank.entrySet().stream()
            .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue())) // Sort by score descending
            .forEach(entry -> System.out.printf("User: %s, Score: %.4f%n", entry.getKey().getName(), entry.getValue()));

        GraphVisualizer visualizer = new GraphVisualizer();
        visualizer.visualizeGraphically(network);
    }
}

