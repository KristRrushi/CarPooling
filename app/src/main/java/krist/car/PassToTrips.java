package krist.car;

public class PassToTrips {
    String emri;
    String phone;
    String mosha;
    String gjinia;
    String imageUrl;







    public PassToTrips(String name, String phonel, String mosha, String gjinia) {
        this.emri = name;
        this.phone = phonel;
        this.mosha = mosha;
        this.gjinia = gjinia;
    }


    public String getMosha() {
        return mosha;
    }

    public void setMosha(String mosha) {
        this.mosha = mosha;
    }

    public String getGjinia() {
        return gjinia;
    }

    public void setGjinia(String gjinia) {
        this.gjinia = gjinia;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
