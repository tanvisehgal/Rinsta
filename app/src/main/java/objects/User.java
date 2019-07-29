package objects;

import java.util.List;

public class User extends UserBasic {
    private int numFollowers;
    private int numFollowing;
    private String description;
    //private List<Post> posts;

    public User() {

    }

    public User(int profilePic, String email, int numFollowers, int numFollowing,
                String description) {
        super(profilePic, email);
        this.numFollowers = numFollowers;
        this.numFollowing = numFollowing;
        this.description = description;
        //this.posts = posts;
    }

    public int getNumFollowers() {
        return numFollowers;
    }

    public void setNumFollowers(int numFollowers) {
        this.numFollowers = numFollowers;
    }

    public int getNumFollowing() {
        return numFollowing;
    }

    public void setNumFollowing(int numFollowing) {
        this.numFollowing = numFollowing;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public List<Post> getPosts() {
//        return posts;
//    }
//
//    public void setPosts(List<Post> posts) {
//        this.posts = posts;
//    }
}
