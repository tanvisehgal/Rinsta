package objects;

public class Post {
    private int imageId;
    private int numLikes;
    private int numComments;
    private int timeStamp;
    private UserBasic user;
    private String caption;

    public Post(int imageId, int numLikes, int numComments, int timeStamp, UserBasic user,
                String caption) {
        this.imageId = imageId;
        this.numLikes = numLikes;
        this.numComments = numComments;
        this.timeStamp = timeStamp;
        this.user = user;
        this.caption = caption;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getNumLikes() {
        return Integer.toString(numLikes);
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public String getNumComments() {
        return numComments + "comments";
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }

    public String getTimeStamp() {
        return Integer.toString(timeStamp);
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public UserBasic getUser() {
        return user;
    }

    public void setUser(UserBasic user) {
        this.user = user;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
