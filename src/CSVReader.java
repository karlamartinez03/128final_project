import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CSVReader {
    private final String filePath;

    public CSVReader(String filePath) {
        this.filePath = filePath;
    }

    public HashMap<String, User> parseUsers() {
        HashMap<String, User> users = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine(); // Read the header line
            if (headerLine == null) {
                throw new IOException("CSV file is empty");
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                
                // Parse fields
                String id = fields[0];
                String name = fields[1];
                String nationality = fields[2];
                String city = fields[3];
                double latitude = Double.parseDouble(fields[4]);
                double longitude = Double.parseDouble(fields[5]);
                String gender = fields[6];
                String ethnicGroup = fields[7];
                int age = Integer.parseInt(fields[8]);
                double englishGrade = Double.parseDouble(fields[9]);
                double mathGrade = Double.parseDouble(fields[10]);
                double sciencesGrade = Double.parseDouble(fields[11]);
                double languageGrade = Double.parseDouble(fields[12]);
                int portfolioRating = Integer.parseInt(fields[13]);
                int coverLetterRating = Integer.parseInt(fields[14]);
                int refLetterRating = Integer.parseInt(fields[15]);

                // Create a User object
                User user = new User(id, name, nationality, city, latitude, longitude, gender, ethnicGroup,
                                     age, englishGrade, mathGrade, sciencesGrade, languageGrade,
                                     portfolioRating, coverLetterRating, refLetterRating);

                // Add to the map
                users.put(id, user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}



