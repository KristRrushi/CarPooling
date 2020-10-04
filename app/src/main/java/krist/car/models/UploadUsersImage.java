package krist.car.models;

public class UploadUsersImage {


    private String imageUrl;
    private String imageCarUrl;


    public String getImageCarUrl() {
        return imageCarUrl;
    }

    public void setImageCarUrl(String imageCarUrl) {
        this.imageCarUrl = imageCarUrl;
    }

    public UploadUsersImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UploadUsersImage(){}

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
