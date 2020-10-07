package krist.car.models;

public class PassToTripsModel {
    private String emri;
    private String phone;
    private String mosha;
    private String gjinia;
    private String imageUrl;

    public PassToTripsModel(String name, String phonel, String mosha, String gjinia, String img) {
        this.emri = name;
        this.phone = phonel;
        this.mosha = mosha;
        this.gjinia = gjinia;
        this.imageUrl = img;
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

    public PassToTripsModel(){}

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
