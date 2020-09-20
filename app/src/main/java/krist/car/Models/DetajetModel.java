package krist.car.Models;

public class DetajetModel {

   private String id;
   private String markaMak;
   private String modeliMak;
   private String targaMak;
   private String emri;
   private String phone;
   private String birthday;
   private String gener;
   private String personalIdNumber;
   private String ngjyraMak;


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

    public String getNgjyraMak() {
        return ngjyraMak;
    }

    public void setNgjyraMak(String ngjyraMak) {
        this.ngjyraMak = ngjyraMak;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getMarkaMak() {
        return markaMak;
    }

    public void setMarkaMak(String markaMak) {
        this.markaMak = markaMak;
    }

    public String getModeliMak() {
        return modeliMak;
    }

    public void setModeliMak(String modeliMak) {
        this.modeliMak = modeliMak;
    }

    public String getTargaMak() {
        return targaMak;
    }

    public void setTargaMak(String targaMak) {
        this.targaMak = targaMak;
    }

    public String getEmri() {
        return emri;
    }

    public void setEmri(String emri) {
        this.emri = emri;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public DetajetModel(String id, String emri, String phone, String markaMak, String modeliMak, String targaMak) {

        this.id = id;
        this.markaMak = markaMak;
        this.modeliMak = modeliMak;
        this.targaMak = targaMak;
        this.emri = emri;
        this.phone = phone;
    }

    public DetajetModel(String emri, String tel){

        this.emri = emri;
        this.phone = tel;

    }

    public DetajetModel(){}

}
