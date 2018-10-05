package krist.car;

public class Upload {
    private String imageCarUrl;

    private String userId;








    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Upload(String imageCarUrl){
        this.imageCarUrl = imageCarUrl;
    }

    public Upload(){
        //empty constructor for firebase
    }


    public String getImageCarUrl() {
        return imageCarUrl;
    }

    public void setImageCarUrl(String imageCarUrl) {
        this.imageCarUrl = imageCarUrl;
    }
}
