package krist.car.models;

public class RegisterCarModel {
    private String carMarks;
    private String carModel;
    private String carPlate;
    private String carColor;
    private String carImgRef;

    public RegisterCarModel(String carMarks, String carModel, String carPlate, String carColor, String carImgRef) {
        this.carMarks = carMarks;
        this.carModel = carModel;
        this.carPlate = carPlate;
        this.carColor = carColor;
        this.carImgRef = carImgRef;
    }

    public String getCarMarks() {
        return carMarks;
    }

    public void setCarMarks(String carMarks) {
        this.carMarks = carMarks;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarImgRef() {
        return carImgRef;
    }

    public void setCarImgRef(String carImgRef) {
        this.carImgRef = carImgRef;
    }
}
