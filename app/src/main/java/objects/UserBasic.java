package objects;

public class UserBasic {
    private int profilepic;
    private String email;

    public UserBasic() {

    }

    public UserBasic(int profilepic, String email) {
        this.profilepic = profilepic;
        this.email = email;
    }

    public int getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(int profilepic) {
        this.profilepic = profilepic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
