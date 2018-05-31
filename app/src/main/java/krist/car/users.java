package krist.car;

/**
 * Created by pampers on 1/12/2018.
 */

public class users {

    String userName;
    String userPhone;
    String id;




    public users(String id,String userName, String userPhone) {

        this.id = id;
        this.userName = userName;
        this.userPhone = userPhone;


    }




    public String getId(){return id;}

    public String getUserName() {
        return userName;
    }

    public String getUserPhone() {return userPhone; }
}
