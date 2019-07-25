package objects;

public class Post {
  //  private int postId;
    private int imageid;
    private int numLikes;
    private int numComments;
    private int timestamp;
    private String username;
    //private UserBasic user;
    private String caption;

    public Post() {

    }

    public Post(int imageid, int numLikes, int numComments, int timestamp, String username, String caption) {
        this.imageid = imageid;
        this.numLikes = numLikes;
        this.numComments = numComments;
        this.timestamp = timestamp;
        this.username = username;
        this.caption = caption;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public int getNumComments() {
        return numComments;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
