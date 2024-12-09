import java.util.HashMap;
import java.util.Map;

public class User {
     private String id;
    private String name;
    private Map<String, String> metadata; // e.g., "location" -> "New York"

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.metadata = new HashMap<>();
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    public String getCity() {
        return city;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getGender() {
        return gender;
    }

    public String getEthnicGroup() {
        return ethnicGroup;
    }

    public int getAge() {
        return age;
    }

    public double getEnglishGrade() {
        return englishGrade;
    }

    public double getMathGrade() {
        return mathGrade;
    }

    public double getSciencesGrade() {
        return sciencesGrade;
    }

    public double getLanguageGrade() {
        return languageGrade;
    }

    public int getPortfolioRating() {
        return portfolioRating;
    }

    public int getCoverLetterRating() {
        return coverLetterRating;
    }

    public int getRefLetterRating() {
        return refLetterRating;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nationality='" + nationality + '\'' +
                ", city='" + city + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", gender='" + gender + '\'' +
                ", ethnicGroup='" + ethnicGroup + '\'' +
                ", age=" + age +
                ", englishGrade=" + englishGrade +
                ", mathGrade=" + mathGrade +
                ", sciencesGrade=" + sciencesGrade +
                ", languageGrade=" + languageGrade +
                ", portfolioRating=" + portfolioRating +
                ", coverLetterRating=" + coverLetterRating +
                ", refLetterRating=" + refLetterRating +
                '}';
    }

    public void setConnectionCount(int count) {
        this.connectionCount = count;
    }

    public int getConnectionCount() {
        return connectionCount;
    }

    @Override
    public int compareTo(User other) {
        return Integer.compare(other.connectionCount, this.connectionCount);
    }

    @Override
    public String toString() {
        return String.format("%s (Connections: %d)", name, connectionCount);
    }

}

