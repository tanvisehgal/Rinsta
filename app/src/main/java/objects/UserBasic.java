package objects;

public class UserBasic {
    private String profilepic;
    private String email;

    public UserBasic() {

    }

    public UserBasic(String profilepic, String email) {
        this.profilepic = profilepic;
        this.email = email;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
