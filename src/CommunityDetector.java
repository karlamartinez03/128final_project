package src;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CommunityDetector {
    private HashMap<User, User> parent;

    public CommunityDetector(Set<User> users) {
        parent = new HashMap<>();
        for (User user : users) {
            parent.put(user, user);
        }
    }

    public User find(User user) {
        if (!parent.get(user).equals(user)) {
            parent.put(user, find(parent.get(user))); // Path compression
        }
        return parent.get(user);
    }

    public void union(User user1, User user2) {
        User root1 = find(user1);
        User root2 = find(user2);
        if (!root1.equals(root2)) {
            parent.put(root1, root2);
        }
    }

    public HashMap<User, Set<User>> getCommunities() {
        HashMap<User, Set<User>> communities = new HashMap<>();
        for (User user : parent.keySet()) {
            User root = find(user);
            communities.putIfAbsent(root, new HashSet<>());
            communities.get(root).add(user);
        }
        return communities;
    }
}

