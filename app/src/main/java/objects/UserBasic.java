package objects;

public class UserBasic {
    private int profilePic;
    private String username;

    public UserBasic(int profilePic, String username) {
        this.profilePic = profilePic;
        this.username = username;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
