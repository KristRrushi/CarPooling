package krist.car;

/**
 * Created by pampers on 12/19/2017.
 */

public class usersPost {


    private String vNisja;
    private String vMberritja;
    private  String data;
    private   String ora;
    private String vendet;

    public  usersPost (String vNisja, String vMberritja, String data, String ora, String vendet){


        this.vNisja = vNisja;
        this.vMberritja = vMberritja;
        this.data = data;
        this.ora = ora;
        this.vendet = vendet;


    }


    public String getvNisja() {
        return vNisja;
    }

    public String getvMberritja() {
        return vMberritja;
    }

    public String getData() {
        return data;
    }

    public String getOra() {
        return ora;
    }

    public String getVendet() {
        return vendet;
    }
}
