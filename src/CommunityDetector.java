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
    //Hash map the maps a singular user to the whole users in the hashmap

    public User find(User user) {
        if (!parent.get(user).equals(user)) {
            parent.put(user, find(parent.get(user))); 
        }
        return parent.get(user);
    }
    //If the when the hash map tries to get the user and it does not equal the user
    //Then the parent should but the user as the key and assign the value until the parents.get.user = user
    //if the parents it is in the hash map then the hashmap will get the user

    public void union(User user1, User user2) {
        User root1 = find(user1);
        User root2 = find(user2);
        if (!root1.equals(root2)) {
            parent.put(root1, root2);
        }
    }
    //if the users are not identical then you should put user one, with value of where you found user 2

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

