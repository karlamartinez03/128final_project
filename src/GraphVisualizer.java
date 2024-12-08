// public class GraphVisualizer {
//     // A method to visualize the network in a basic way (console-based or simple display)
//     public void displayNetwork(SocialNetwork network) {
//         // For simplicity, just print out users and their connections
//         System.out.println("Displaying Network:");

//         for (User user : network.getUsers()) {
//             System.out.println(user.getName() + " (" + user.getId() + "):");

//             for (Connection conn : network.getConnections(user)) {
//                 System.out.println("  - Connected to " + conn.getUser2().getName() + " with weight " + conn.getWeight());
//             }
//         }
//     }

//     // Placeholder for graphical visualization (e.g., using JavaFX or Graphviz)
//     public void visualizeGraphically(SocialNetwork network) {
//         // Implement graphical visualization using JavaFX or another library here
//         System.out.println("Graphical visualization of the network would go here.");
//     }

// }
import edu.macalester.graphics.*;
import edu.macalester.graphics.events.*;

import java.awt.Color;
import java.util.HashMap;
import java.util.Random;

public class GraphVisualizer {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final double NODE_RADIUS = 20;

    // Method to create a graphical visualization
    public void visualizeGraphically(SocialNetwork network) {
        CanvasWindow canvas = new CanvasWindow("Social Network Graph", WIDTH, HEIGHT);
        HashMap<User, Point> userPositions = new HashMap<>();
        Random random = new Random();

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
        
            handleMouseClick(event, userPositions, canvas, network));
    }

    private void handleMouseClick(MouseButtonEvent event, HashMap<User, Point> userPositions, CanvasWindow canvas, SocialNetwork network) {
        Point clickPoint = new Point(event.getPosition().getX(), event.getPosition().getY());
    
        for (User user : userPositions.keySet()) {
            Point userPosition = userPositions.get(user);
            double distance = clickPoint.distance(userPosition);
    
            if (distance <= NODE_RADIUS) {
                // Clear the canvas and highlight the selected user and their connections
                canvas.removeAll();
                highlightUser(user, userPositions, canvas, network);
                break;
            }
        }
    }
    

    private void highlightUser(User user, HashMap<User, Point> userPositions, CanvasWindow canvas, SocialNetwork network) {
        Point userPoint = userPositions.get(user);

        // Draw the selected user
        Ellipse selectedNode = new Ellipse(
            userPoint.getX() - NODE_RADIUS, 
            userPoint.getY() - NODE_RADIUS, 
            2 * NODE_RADIUS, 
            2 * NODE_RADIUS
        );
        selectedNode.setFillColor(Color.RED);
        canvas.add(selectedNode);

        // Add user name
        GraphicsText userName = new GraphicsText(user.getName(), userPoint.getX(), userPoint.getY());
        userName.setFontSize(10);
        userName.setAnchor(userName.getCenter());
        canvas.add(userName);

        // Draw the user's connections
    for (Connection connection : network.getConnections(user)) {
        User neighbor = connection.getUser2();
        Point neighborPoint = userPositions.get(neighbor);

        if (neighborPoint != null) {
            // Draw a line between the user and their neighbor
            Line connectionLine = new Line(userPoint, neighborPoint);
            connectionLine.setStrokeWidth(2);
            connectionLine.setStrokeColor(Color.BLUE);
            canvas.add(connectionLine);

            // Optionally, add the weight of the connection
            double midX = (userPoint.getX() + neighborPoint.getX()) / 2;
            double midY = (userPoint.getY() + neighborPoint.getY()) / 2;
            GraphicsText connectionWeight = new GraphicsText(String.valueOf(connection.getWeight()), midX, midY);
            connectionWeight.setFontSize(10);
            connectionWeight.setAnchor(connectionWeight.getCenter());
            canvas.add(connectionWeight);
    }
}
    }}