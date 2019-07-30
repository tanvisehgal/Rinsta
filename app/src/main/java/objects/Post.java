package objects;

public class Post {
    private String imageid;
    private int numLikes;
    private int numComments;
    private String timeStamp;
    private String email;
    private String caption;

    public Post() {

    }

    public Post(String imageid, int numLikes, int numComments, String timeStamp, String email, String caption) {
        this.imageid = imageid;
        this.numLikes = numLikes;
        this.numComments = numComments;
        this.timeStamp = timeStamp;
        this.email = email;
        this.caption = caption;
    }

    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
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

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
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
