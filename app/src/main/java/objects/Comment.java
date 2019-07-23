package objects;

public class Comment {
    private String comment;
    private int timeStamp;
    private UserBasic user;

    public Comment(String comment, int timeStamp, UserBasic user) {
        this.comment = comment;
        this.timeStamp = timeStamp;
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getTimeStamp() {
        return timeStamp;
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
}
