import edu.macalester.graphics.*;
import edu.macalester.graphics.events.*;

import java.awt.Color;
import java.util.HashMap;
import java.util.Random;

public class GraphVisualizer {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final double NODE_RADIUS = 20;
    private static final int LEADERBOARD_WIDTH = 230;
    private static final int LEADERBOARD_HEIGHT = 150;
    
    Random random = new Random();

    public void visualizeGraphically(SocialNetwork network) {
        HashMap<User, Point> userPositions = new HashMap<>();
        CanvasWindow canvas = new CanvasWindow("Social Network Graph", WIDTH, HEIGHT);
        NetworkAnalyzer leaderboard = new NetworkAnalyzer(network);

        leaderboard.displayLeaderboard(canvas, LEADERBOARD_WIDTH, LEADERBOARD_HEIGHT); 

        //SO THAT THEY DO NOT OVER LAP
    for (User user : network.getUsers()) {
        Point newPoint;
        boolean positionValid;
        do {
            double x = random.nextDouble() * (WIDTH - 2 * NODE_RADIUS) + NODE_RADIUS;
            double y = random.nextDouble() * (HEIGHT - 2 * NODE_RADIUS) + NODE_RADIUS;
            newPoint = new Point(x, y);

            positionValid = true;

        
            if (x < LEADERBOARD_WIDTH + NODE_RADIUS && y < LEADERBOARD_HEIGHT + NODE_RADIUS) {
                positionValid = false;
            }

            //position does not overlap with any existing nodes
            for (Point existingPoint : userPositions.values()) {
                if (existingPoint.distance(newPoint) < 3 * NODE_RADIUS) {
                    positionValid = false;
                    break;
                }
            }
        } while (!positionValid);

    
        userPositions.put(user, newPoint);
    }



        //draw connections
        for (User user : network.getUsers()) {
            Point userPoint = userPositions.get(user);
            for (Connection connection : network.getConnections(user)) {
                User neighbor = connection.getUser2(); //WHY IS CONNECTING TO EVERYONE
                Point neighborPoint = userPositions.get(neighbor); 
                double weight = connection.getWeight();

                
                Line edge = new Line(userPoint.getX(), userPoint.getY(), neighborPoint.getX(), neighborPoint.getY());
                edge.setStrokeWidth(3);
                edge.setStrokeColor(Color.LIGHT_GRAY); 
                canvas.add(edge);

                double midX = (userPoint.getX() + neighborPoint.getX()) / 2;
                double midY = (userPoint.getY() + neighborPoint.getY()) / 2;
                GraphicsText weightText = new GraphicsText(String.valueOf(weight), midX, midY);
                weightText.setFontSize(12);
                weightText.setFillColor(Color.DARK_GRAY);
                canvas.add(weightText);
            }
        }

        for (User user : network.getUsers()) {
            Point position = userPositions.get(user);
            double nodeSize = NODE_RADIUS * 3;
            Ellipse node = new Ellipse(
                position.getX() - nodeSize / 2, 
                position.getY() - nodeSize / 2, 
                nodeSize,
                nodeSize
            );
            
            int minBrightness = 100;
            node.setFillColor(new Color(minBrightness + random.nextInt(156), minBrightness + random.nextInt(156), minBrightness + random.nextInt(156))); 
            canvas.add(node);

            GraphicsText userName = new GraphicsText(user.getName());
            userName.setFontSize(12);
            
            userName.setFontStyle(FontStyle.BOLD);
            userName.setAnchor(userName.getCenter());
            userName.setCenter(position);
            canvas.add(userName);
        }

        canvas.onMouseDown(event -> 
        
            handleMouseClick(event, userPositions, canvas, network, leaderboard));
    }

    private boolean isFilteredView = false; 

    private void handleMouseClick(MouseButtonEvent event, HashMap<User, Point> userPositions, CanvasWindow canvas, SocialNetwork network, NetworkAnalyzer leaderboard) {
        Point clickPoint = new Point(event.getPosition().getX(), event.getPosition().getY());
    
        if (isFilteredView) {
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
                //highlights the selected user and their connections
                canvas.removeAll();
                leaderboard.displayLeaderboard(canvas, LEADERBOARD_WIDTH, LEADERBOARD_HEIGHT); 
                highlightUser(user, userPositions, canvas, network);
                isFilteredView = true;
                return;
            }
        }
    }

    private void drawGraph(HashMap<User, Point> userPositions, SocialNetwork network, CanvasWindow canvas) {
        for (User user : userPositions.keySet()) {
            Point userPoint = userPositions.get(user);
    
            for (Connection connection : network.getConnections(user)) {
                User neighbor = connection.getUser2();
                Point neighborPoint = userPositions.get(neighbor);
    
                Line line = new Line(userPoint.getX(), userPoint.getY(), neighborPoint.getX(), neighborPoint.getY());
                line.setStrokeColor(Color.LIGHT_GRAY);
                line.setStrokeWidth(3);
                canvas.add(line);

                double weight = connection.getWeight();

                double midX = (userPoint.getX() + neighborPoint.getX()) / 2;
                double midY = (userPoint.getY() + neighborPoint.getY()) / 2;
                GraphicsText weightText = new GraphicsText(String.valueOf(weight), midX, midY);
                weightText.setFontSize(12);
                weightText.setFillColor(Color.DARK_GRAY);
                canvas.add(weightText);

            }
           
            double nodeSize = NODE_RADIUS * 3; 
    
            Ellipse node = new Ellipse(userPoint.getX() - nodeSize / 2, userPoint.getY() - nodeSize / 2, nodeSize, nodeSize);
            int minBrightness = 100;
            node.setFillColor(new Color(minBrightness + random.nextInt(156), minBrightness + random.nextInt(156), minBrightness + random.nextInt(156))); 

            GraphicsText userName = new GraphicsText(user.getName());
            userName.setFontSize(12);
            userName.setFontStyle(FontStyle.BOLD);
            userName.setAnchor(userName.getCenter());
            userName.setCenter(userPoint);
            canvas.add(node);
            canvas.add(userName);
            
        }
    }

    private void highlightUser(User user, HashMap<User, Point> userPositions, CanvasWindow canvas, SocialNetwork network) {
        Point userPoint = userPositions.get(user);
        double nodeSize = NODE_RADIUS * 3;

        //highlight the selected user
        Ellipse selectedNode = new Ellipse(
            userPoint.getX() - nodeSize / 2, 
            userPoint.getY() - nodeSize / 2, 
            nodeSize, 
            nodeSize
        );        
        selectedNode.setFillColor(Color.RED);
    
        GraphicsText userName = new GraphicsText(user.getName());
        userName.setFontSize(12);
        userName.setFontStyle(FontStyle.BOLD);
        userName.setAnchor(userName.getCenter());
        userName.setCenter(userPoint);
        canvas.add(selectedNode);
        canvas.add(userName);
    
        //draw connections and highlight connected users
        for (Connection connection : network.getConnections(user)) {
            User neighbor = connection.getUser2();
            Point neighborPoint = userPositions.get(neighbor);
            
            //draw connection line
            Line connectionLine = new Line(
                userPoint.getX(), userPoint.getY(),
                neighborPoint.getX(), neighborPoint.getY()
            );
            connectionLine.setStrokeColor(Color.BLACK);
            connectionLine.setStrokeWidth(2);
            canvas.add(connectionLine);
    
            //highlight the connected user
            Ellipse neighborNode = new Ellipse(
                neighborPoint.getX() - nodeSize / 2,
                neighborPoint.getY() - nodeSize / 2,
                nodeSize,
                nodeSize
            );

            neighborNode.setFillColor(new Color(174, 194, 198));
    
            GraphicsText neighborName = new GraphicsText(neighbor.getName());
            neighborName.setFontSize(12);
            neighborName.setFontStyle(FontStyle.BOLD);
            neighborName.setAnchor(neighborName.getCenter());
            neighborName.setCenter(neighborPoint);
            canvas.add(neighborNode);
            canvas.add(neighborName);
            
            double midX = (userPoint.getX() + neighborPoint.getX()) / 2;
            double midY = (userPoint.getY() + neighborPoint.getY()) / 2;
            GraphicsText connectionWeight = new GraphicsText(String.valueOf(connection.getWeight()), midX, midY);
            connectionWeight.setFontSize(10);
            connectionWeight.setAnchor(connectionWeight.getCenter());
            canvas.add(connectionWeight);
        }
    }

}
