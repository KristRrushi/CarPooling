package krist.car.ProfileInfo.Models;

public class ProfileInfoModel {
    private String emri;
    private String gener;
    private String id;
    private String personalIdNumber;
    private String phone;
    private int rating;
    private String userImgRef;
    private String birthday;

    public ProfileInfoModel(){}

    public ProfileInfoModel(String emri, String gener, String id, String personalIdNumber, String phone, int rating, String userImgRef, String birthday) {
        this.emri = emri;
        this.gener = gener;
        this.id = id;
        this.personalIdNumber = personalIdNumber;
        this.phone = phone;
        this.rating = rating;
        this.userImgRef = userImgRef;
        this.birthday = birthday;
    }

    public String getEmri() {
        return emri;
    }

    public void setEmri(String emri) {
        this.emri = emri;
    }

    public String getGener() {
        return gener;
    }

    public void setGener(String gener) {
        this.gener = gener;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonalIdNumber() {
        return personalIdNumber;
    }

    public void setPersonalIdNumber(String personalIdNumber) {
        this.personalIdNumber = personalIdNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUserImgRef() {
        return userImgRef;
    }

    public void setUserImgRef(String userImgRef) {
        this.userImgRef = userImgRef;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
