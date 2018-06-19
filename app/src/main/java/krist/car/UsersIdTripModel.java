package krist.car;

import java.util.ArrayList;

public class UsersIdTripModel {
    private ArrayList<String> usersId;
    private String userId;

    public UsersIdTripModel(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
