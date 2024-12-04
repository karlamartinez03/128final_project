public class Connection {
    private User user1;
    private User user2;
    private double weight; // Strength of the relationship

    public Connection(User user1, User user2, double weight) {
        this.user1 = user1;
        this.user2 = user2;
        this.weight = weight;
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
