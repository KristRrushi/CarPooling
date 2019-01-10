package krist.car;

/**
 * Created by pampers on 1/12/2018.
 */

public class users {

    String emri;
    String phone;
    String id;
    String birthday;
    String gener;
    String personalIdNumber;
    Float rating;


    public users(){};

    public users(String id, String emri, String phone, String birthday, String gener, String personalIdNumber) {

        this.id = id;
        this.emri = emri;
        this.phone = phone;
        this.birthday = birthday;
        this.gener = gener;
        this.personalIdNumber = personalIdNumber;
        this.rating = 0f;
    }


    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGener() {
        return gener;
    }

    public void setGener(String gener) {
        this.gener = gener;
    }

    public String getPersonalIdNumber() {
        return personalIdNumber;
    }

    public void setPersonalIdNumber(String personalIdNumber) {
        this.personalIdNumber = personalIdNumber;
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

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

}
