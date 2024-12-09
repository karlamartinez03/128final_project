import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static List<User> readCSV(String filePath) throws IOException {
        List<User> users = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine(); // Skip the header line
            if (headerLine == null) {
                throw new IOException("The file is empty.");
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                // Parse each value to the corresponding field
                int id = Integer.parseInt(values[0]);
                String name = values[1];
                String nationality = values[2];
                String city = values[3];
                double latitude = Double.parseDouble(values[4]);
                double longitude = Double.parseDouble(values[5]);
                String gender = values[6];
                String ethnicGroup = values[7];
                int age = Integer.parseInt(values[8]);
                double englishGrade = Double.parseDouble(values[9]);
                double mathGrade = Double.parseDouble(values[10]);
                double sciencesGrade = Double.parseDouble(values[11]);
                double languageGrade = Double.parseDouble(values[12]);
                int portfolioRating = Integer.parseInt(values[13]);
                int coverLetterRating = Integer.parseInt(values[14]);
                int refLetterRating = Integer.parseInt(values[15]);

                // Create and add a new User object
                User user = new User(id, name, nationality, city, latitude, longitude, gender, ethnicGroup, age,
                        englishGrade, mathGrade, sciencesGrade, languageGrade, portfolioRating, coverLetterRating,
                        refLetterRating);
                users.add(user);
            }
        }

        return users;
    }

    public static void main(String[] args) {
        String filePath = "path/to/your/user-dataset.csv";

        try {
            List<User> users = readCSV(filePath);

            // Example: Print the parsed data
            for (User user : users) {
                System.out.println(user);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}


