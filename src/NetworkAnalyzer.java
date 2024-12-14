import java.util.*;
import java.util.List;
import java.awt.*;
import edu.macalester.graphics.*;
import edu.macalester.graphics.Rectangle;

// Data Structure used: Priority queues, Hash Map,

public class NetworkAnalyzer {
    private SocialNetwork socialNetwork;

    public NetworkAnalyzer(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    HashMap<User, Integer> calculateDegreeCentrality() {
        HashMap<User, Integer> centrality = new HashMap<>();
        for (User user : socialNetwork.getUsers()) {
            centrality.put(user, socialNetwork.getConnections(user).size());
        }
        return centrality;
    }

    public List<User> getLeaderboard() {
        NetworkAnalyzer analyzer = new NetworkAnalyzer(socialNetwork);
        Map<User, Double> pageRankScores = analyzer.calculatePageRank(100, 0.85);

        double totalScore = pageRankScores.values().stream().mapToDouble(Double::doubleValue).sum();

        HashMap<User, Double> normalizedScores = new HashMap<>();
        for (Map.Entry<User, Double> entry : pageRankScores.entrySet()) {
            double percentage = (entry.getValue() / totalScore) * 100;
            normalizedScores.put(entry.getKey(), percentage);
        }

        // Sort users by their PageRank percentage in descending order
        List<User> users = new ArrayList<>(normalizedScores.keySet());
        users.sort((u1, u2) -> Double.compare(normalizedScores.get(u2), normalizedScores.get(u1)));

        // Select the top 5 users or those with tied scores
        List<User> topUsers = new ArrayList<>();
        int rank = 1;
        double lastScore = -1;

        for (User user : users) {
            double userScore = normalizedScores.get(user);

            if (rank > 5 && userScore != lastScore) {
                break;
            }

            user.setPageRankScore(userScore); // Store the PageRank score in the User object for display
            topUsers.add(user);
            lastScore = userScore;
            rank++;
        }

        return topUsers;

    }

    public void displayLeaderboard(CanvasWindow canvas, int leaderWidth, int leaderHeight) {

        List<User> topUsers = getLeaderboard();

        Rectangle leaderboardBackground = new Rectangle(10, 10, leaderWidth, leaderHeight);
        leaderboardBackground.setFillColor(new Color(0, 0, 0, 50));
        leaderboardBackground.setStrokeColor(Color.BLACK);
        canvas.add(leaderboardBackground);

        GraphicsText title = new GraphicsText("Leaderboard", 15, 30);
        title.setFontSize(14);
        title.setFillColor(Color.WHITE);
        canvas.add(title);

        int yOffset = 50;
        int rank = 1;
        double lastScore = -1;

        for (User user : topUsers) {
            double userScore = user.getPageRankScore();

            if (userScore != lastScore) {
                lastScore = userScore;
            }

            String entryText = rank + ". " + user.getName() + " (" + String.format("%.2f", userScore) + "%)";
            GraphicsText userText = new GraphicsText(entryText, 20, yOffset);
            userText.setFontSize(14);
            userText.setFillColor(Color.WHITE);
            canvas.add(userText);

            yOffset += 25;
            rank++;
        }
    }

    public Map<User, Double> calculatePageRank(int iterations, double dampingFactor) {
        Map<User, Double> pageRank = new HashMap<>();
        int totalUsers = socialNetwork.getUsers().size();
        double initialScore = 1.0 / totalUsers;

        for (User user : socialNetwork.getUsers()) {
            pageRank.put(user, initialScore);
        }

        for (int i = 0; i < iterations; i++) {
            Map<User, Double> newPageRank = new HashMap<>();

            for (User user : socialNetwork.getUsers()) {
                double rank = (1 - dampingFactor) / totalUsers;
                for (Connection connection : socialNetwork.getConnections(user)) {
                    User neighbor = connection.getUser2();
                    double connectionWeight = connection.getWeight();
                    double neighborRank = pageRank.get(neighbor);
                    double totalConnections = socialNetwork.getConnections(neighbor).size();

                    if (totalConnections > 0) {
                        rank += dampingFactor * (neighborRank * connectionWeight / totalConnections);
                    }
                }
                newPageRank.put(user, rank);
            }

            double totalRank = newPageRank.values().stream().mapToDouble(Double::doubleValue).sum();
            for (Map.Entry<User, Double> entry : newPageRank.entrySet()) {
                newPageRank.put(entry.getKey(), entry.getValue() / totalRank);
            }

            pageRank = newPageRank;
        }

        return pageRank;
    }


    public double calculateWeight(User user1, User user2) {
        // Example similarity calculation (customize as needed):
        double weight = 0.0;

        // Compare nationality
        if (user1.getNationality().equals(user2.getNationality())) {
            weight += 1.0;
        }
        // Compare grades (example: math grade similarity)
        // weight += 1.0 / (1 + Math.abs(user1.getMathGrade() - user2.getMathGrade()));
        if (user1.getAge() == user2.getAge()) {
            weight += 1.0;
        }
         // Compare city
         if (user1.getCity().equals(user2.getCity())) {
            weight += 1.0;
        }

        return weight;
    }
}

