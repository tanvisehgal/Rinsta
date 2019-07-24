package objects;

public class UserBasic {
    private int profilepic;
    private String username;

    public UserBasic(int profilepic, String username) {
        this.profilepic = profilepic;
        this.username = username;
    }

    public int getProfilePic() {
        return profilepic;
    }

    public void setProfilePic(int profilepic) {
        this.profilepic = profilepic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
