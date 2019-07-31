package objects;

public class UserBasic {
    private int profilepic;
    private String username;

    public UserBasic() {

    }

    public UserBasic(int profilepic, String username) {
        this.profilepic = profilepic;
        this.username = username;
    }

    public int getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(int profilepic) {
        this.profilepic = profilepic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
