package utils;

import objects.UserBasic;

public class UsersAdapterItem {

    UserBasic user;

    public UsersAdapterItem(UserBasic user) {
        this.user = user;
    }

    public UserBasic getUser() {
        return user;
    }

    public void setUser(UserBasic user) {
        this.user = user;
    }
}
