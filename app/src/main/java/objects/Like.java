package objects;

public class Like {
    private int timeStamp;
    private UserBasic user;

    public Like(int timeStamp, UserBasic user) {
        this.timeStamp = timeStamp;
        this.user = user;
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
