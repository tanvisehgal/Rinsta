package objects;

public class Post {
  //  private int postId;
    private int imageid;
    private int numLikes;
    private int numComments;
    private int timeStamp;
    private String email;
    //private UserBasic user;
    private String caption;

    public Post() {

    }

    public Post(int imageid, int numLikes, int numComments, int timeStamp, String email, String caption) {
        this.imageid = imageid;
        this.numLikes = numLikes;
        this.numComments = numComments;
        this.timeStamp = timeStamp;
        this.email = email;
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

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
