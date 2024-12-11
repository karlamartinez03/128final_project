import edu.macalester.graphics.*;
import edu.macalester.graphics.events.*;

import java.awt.Color;
import java.util.HashMap;
import java.util.Random;

public class GraphVisualizer {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final double NODE_RADIUS = 20;
    private static final int LEADERBOARD_WIDTH = 200;
    private static final int LEADERBOARD_HEIGHT = 150;


    // Method to create a graphical visualization
    public void visualizeGraphically(SocialNetwork network) {
        HashMap<User, Point> userPositions = new HashMap<>();
        Random random = new Random();
        CanvasWindow canvas = new CanvasWindow("Social Network Graph", WIDTH, HEIGHT);
        NetworkAnalyzer leaderboard = new NetworkAnalyzer(network);

        leaderboard.displayLeaderboard(canvas, LEADERBOARD_WIDTH, LEADERBOARD_HEIGHT); 

        // Step 1: Generate random positions for users
        for (User user : network.getUsers()) {
            double x = random.nextDouble() * (WIDTH - 2 * NODE_RADIUS) + NODE_RADIUS;
            double y = random.nextDouble() * (HEIGHT - 2 * NODE_RADIUS) + NODE_RADIUS;
            userPositions.put(user, new Point(x, y));
        }

        // Step 2: Draw connections (edges)
        for (User user : network.getUsers()) {
            Point userPoint = userPositions.get(user);
            for (Connection connection : network.getConnections(user)) {
                User neighbor = connection.getUser2();
                Point neighborPoint = userPositions.get(neighbor);

                // Draw a line representing the connection
                Line edge = new Line(userPoint.getX(), userPoint.getY(), neighborPoint.getX(), neighborPoint.getY());
                edge.setStrokeWidth(2);
                edge.setStrokeColor(Color.LIGHT_GRAY);
                canvas.add(edge);

                // Display weight in the middle of the edge
                double midX = (userPoint.getX() + neighborPoint.getX()) / 2;
                double midY = (userPoint.getY() + neighborPoint.getY()) / 2;
                GraphicsText weightText = new GraphicsText(String.valueOf(connection.getWeight()), midX, midY);
                weightText.setFontSize(12);
                weightText.setFillColor(Color.DARK_GRAY);
                canvas.add(weightText);
            }
        }



        // Draw leaderboard

        

        // Step 3: Draw user nodes
        for (User user : network.getUsers()) {
            Point position = userPositions.get(user);

            // Draw the circle for the user
            Ellipse node = new Ellipse(
                position.getX() - NODE_RADIUS, 
                position.getY() - NODE_RADIUS, 
                2 * NODE_RADIUS, 
                2 * NODE_RADIUS
            );
            node.setFillColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            canvas.add(node);

            // Add the user name inside the circle
            GraphicsText userName = new GraphicsText(user.getName(), position.getX(), position.getY());
            userName.setFontSize(10);
            userName.setAnchor(userName.getCenter());
            canvas.add(userName);
        }

        // Step 4: Add interactivity (e.g., mouse click to highlight a node)
        canvas.onMouseDown(event -> 
        
            handleMouseClick(event, userPositions, canvas, network, leaderboard));
    }

    private boolean isFilteredView = false; // Tracks whether we're in filtered or full view

    private void handleMouseClick(MouseButtonEvent event, HashMap<User, Point> userPositions, CanvasWindow canvas, SocialNetwork network, NetworkAnalyzer leaderboard) {
        Point clickPoint = new Point(event.getPosition().getX(), event.getPosition().getY());
    
        if (isFilteredView) {
            // Reset to full view
            canvas.removeAll();
            drawGraph(userPositions, network, canvas);
            leaderboard.displayLeaderboard(canvas, LEADERBOARD_WIDTH, LEADERBOARD_HEIGHT);
            isFilteredView = false;
            return;
        }
    
        for (User user : userPositions.keySet()) {
            Point userPosition = userPositions.get(user);
            double distance = clickPoint.distance(userPosition);
    
            if (distance <= NODE_RADIUS) {
                // Highlight the selected user and their connections
                canvas.removeAll();
                leaderboard.displayLeaderboard(canvas, LEADERBOARD_WIDTH, LEADERBOARD_HEIGHT); 
                highlightUser(user, userPositions, canvas, network);
                isFilteredView = true;
                return;
            }
        }
    }

    private void drawGraph(HashMap<User, Point> userPositions, SocialNetwork network, CanvasWindow canvas) {
        // Draw all nodes and their connections
        for (User user : userPositions.keySet()) {
            Point userPoint = userPositions.get(user);
    
            // Draw connections
            for (Connection connection : network.getConnections(user)) {
                User neighbor = connection.getUser2();
                Point neighborPoint = userPositions.get(neighbor);
    
                Line line = new Line(userPoint.getX(), userPoint.getY(), neighborPoint.getX(), neighborPoint.getY());
                line.setStrokeColor(Color.LIGHT_GRAY);
                canvas.add(line);
            }
            
    
            // Draw node
            Ellipse node = new Ellipse(userPoint.getX() - NODE_RADIUS, userPoint.getY() - NODE_RADIUS, 2 * NODE_RADIUS, 2 * NODE_RADIUS);
            node.setFillColor(Color.BLUE);
            canvas.add(node);
    
            // Add name
            GraphicsText userName = new GraphicsText(user.getName(), userPoint.getX(), userPoint.getY() - 10);
            userName.setFontSize(10);
            canvas.add(userName);
        }
    }

    private void highlightUser(User user, HashMap<User, Point> userPositions, CanvasWindow canvas, SocialNetwork network) {
        Point userPoint = userPositions.get(user);
    
        // Highlight the selected user
        Ellipse selectedNode = new Ellipse(
            userPoint.getX() - NODE_RADIUS, 
            userPoint.getY() - NODE_RADIUS, 
            2 * NODE_RADIUS, 
            2 * NODE_RADIUS
        );
        selectedNode.setFillColor(Color.RED);
        canvas.add(selectedNode);
    
        // Add user name
        GraphicsText userName = new GraphicsText(user.getName(), userPoint.getX(), userPoint.getY() - 10);
        userName.setFontSize(10);
        userName.setAnchor(userName.getCenter());
        canvas.add(userName);
    
        // Draw connections and highlight connected users
        for (Connection connection : network.getConnections(user)) {
            User neighbor = connection.getUser2();
            Point neighborPoint = userPositions.get(neighbor);
            
            // Draw connection line
            Line connectionLine = new Line(
                userPoint.getX(), userPoint.getY(),
                neighborPoint.getX(), neighborPoint.getY()
            );
            connectionLine.setStrokeColor(Color.BLACK);
            canvas.add(connectionLine);
    
            // Highlight the connected user
            Ellipse neighborNode = new Ellipse(
                neighborPoint.getX() - NODE_RADIUS,
                neighborPoint.getY() - NODE_RADIUS,
                2 * NODE_RADIUS,
                2 * NODE_RADIUS
            );
            neighborNode.setFillColor(Color.BLUE);
            canvas.add(neighborNode);
    
            // Add neighbor's name
            GraphicsText neighborName = new GraphicsText(neighbor.getName(), neighborPoint.getX(), neighborPoint.getY() - 10);
            neighborName.setFontSize(10);
            neighborName.setAnchor(neighborName.getCenter());
            canvas.add(neighborName);

             // Optionally, add the weight of the connection
             double midX = (userPoint.getX() + neighborPoint.getX()) / 2;
             double midY = (userPoint.getY() + neighborPoint.getY()) / 2;
             GraphicsText connectionWeight = new GraphicsText(String.valueOf(connection.getWeight()), midX, midY);
             connectionWeight.setFontSize(10);
             connectionWeight.setAnchor(connectionWeight.getCenter());
             canvas.add(connectionWeight);
        }
    }

}
