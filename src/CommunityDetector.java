import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


/* Data structure utilized -> HASH MAP why?: Fast Lookups: A HashMap allows for constant-time average lookups (O(1)) when querying or updating the parent of a user.
Flexibility: It can dynamically grow as new users are added.
Ease of Use: Mapping users to their parents is straightforward with a HashMap

 * This class is used to get the community
 */
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

