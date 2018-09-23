package krist.car;

/**
 * Created by pampers on 1/12/2018.
 */

public class users {

    String emri;
    String phone;
    String id;

    public users(){};

    public users(String id, String emri, String phone) {

        this.id = id;
        this.emri = emri;
        this.phone = phone;
    }




    public String getId(){return id;}

    public String getEmri() {
        return emri;
    }

    public String getPhone() {return phone; }

    public void setEmri(String emri) {
        this.emri = emri;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setId(String id) {
        this.id = id;
    }
}
