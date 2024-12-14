/*
 * User class to collect the user's characteristics and their connection count
 */
public class User {

    private int id;
    private String name;
    private String nationality;
    private String city;
    private int age;
    private double mathGrade;
    private int connectionCount;
    private double pageRankScore;

    // Constructor
    public User(int id, String name, String nationality, String city, int age) {

        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.city = city;
        this.age = age;
    }

    public int getConnectionCount() {
        return connectionCount;
    }

    public void setConnectionCount(int connectionCount) {
        this.connectionCount = connectionCount;
    }

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


    public int getAge() {
        return age;
    }


    public double getMathGrade() {
        return mathGrade;
    }



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nationality='" + nationality + '\'' +
                ", city='" + city + '\'' +
                ", age=" + age +
                ", mathGrade=" + mathGrade +
                '}';
    }
  

    public double getPageRankScore() {
        return pageRankScore;
    }

    public void setPageRankScore(double pageRankScore) {
        this.pageRankScore = pageRankScore;
    }
}