public class GraphVisualizer {
    // A method to visualize the network in a basic way (console-based or simple display)
    public void displayNetwork(SocialNetwork network) {
        // For simplicity, just print out users and their connections
        System.out.println("Displaying Network:");

        for (User user : network.getUsers()) {
            System.out.println(user.getName() + " (" + user.getId() + "):");

            for (Connection conn : network.getConnections(user)) {
                System.out.println("  - Connected to " + conn.getUser2().getName() + " with weight " + conn.getWeight());
            }
        }
    }

    // Placeholder for graphical visualization (e.g., using JavaFX or Graphviz)
    public void visualizeGraphically(SocialNetwork network) {
        // Implement graphical visualization using JavaFX or another library here
        System.out.println("Graphical visualization of the network would go here.");
    }

}
