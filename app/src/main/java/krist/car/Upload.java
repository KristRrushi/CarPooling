package krist.car;

public class Upload {
    private String mImageUrl;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Upload(String mImageUrl){
        this.mImageUrl = mImageUrl;
    }

    public Upload(){
        //empty constructor for firebase
    }


    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
