package krist.car;

public class DetajetModel {

    String id;
    String markaMak;
    String modeliMak;
    String targaMak;


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

    public DetajetModel(String id,String markaMak, String modeliMak, String targaMak) {

        this.id = id;
        this.markaMak = markaMak;
        this.modeliMak = modeliMak;
        this.targaMak = targaMak;
    }
}
