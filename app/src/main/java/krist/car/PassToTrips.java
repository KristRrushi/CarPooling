package krist.car;

public class PassToTrips {
    String emri;
    String phone;






    public PassToTrips(String name, String phonel) {
        this.emri = name;
        this.phone = phonel;
    }

    public PassToTrips(){

    }

    public String getEmri() {
        return emri;
    }

    public void setEmri(String name) {
        this.emri = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phonel) {
        this.phone = phonel;
    }
}
