import java.util.HashMap;
import java.util.Map;

public class User {
    private int id;
    private String name;
    private String nationality;
    private String city;
    private double latitude;
    private double longitude;
    private String gender;
    private String ethnicGroup;
    private int age;
    private double englishGrade;
    private double mathGrade;
    private double sciencesGrade;
    private double languageGrade;
    private int portfolioRating;
    private int coverLetterRating;
    private int refLetterRating;

    // Constructor
    public User(int id, String name, String nationality, String city, double latitude, double longitude,
                String gender, String ethnicGroup, int age, double englishGrade, double mathGrade,
                double sciencesGrade, double languageGrade, int portfolioRating, int coverLetterRating,
                int refLetterRating) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.gender = gender;
        this.ethnicGroup = ethnicGroup;
        this.age = age;
        this.englishGrade = englishGrade;
        this.mathGrade = mathGrade;
        this.sciencesGrade = sciencesGrade;
        this.languageGrade = languageGrade;
        this.portfolioRating = portfolioRating;
        this.coverLetterRating = coverLetterRating;
        this.refLetterRating = refLetterRating;
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
}


