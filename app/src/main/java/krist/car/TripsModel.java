package krist.car;

import android.net.Uri;
import android.widget.ProgressBar;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pampers on 12/19/2017.
 */


public class TripsModel {


    private String vNisja;
    private String vMberritja;
    private String data;
    private String ora;
    private String vendet;
    private String idShofer;
    private String usersId;
    private String uri;
    private Map<String, Object> map = new HashMap<>();


    private String tripID;


    public TripsModel(){}

    public TripsModel(String idShofer,String vNisja, String vMberritja, String data, String ora, String vendet, String uri,String tripID){

        this.idShofer = idShofer;
        this.vNisja = vNisja;
        this.vMberritja = vMberritja;
        this.data = data;
        this.ora = ora;
        this.vendet = vendet;
        this.uri = uri;

        this.tripID = tripID;

    }

    public TripsModel(String idShofer,String vNisja, String vMberritja, String data, String ora, String vendet, String uri){

        this.idShofer = idShofer;
        this.vNisja = vNisja;
        this.vMberritja = vMberritja;
        this.data = data;
        this.ora = ora;
        this.vendet = vendet;
        this.uri = uri;



    }



    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getvNisja() {
        return vNisja;
    }

    public void setvNisja(String vNisja) {
        this.vNisja = vNisja;
    }

    public String getvMberritja() {
        return vMberritja;
    }

    public void setvMberritja(String vMberritja) {
        this.vMberritja = vMberritja;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public String getVendet() {
        return vendet;
    }

    public void setVendet(String vendet) {
        this.vendet = vendet;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public String getIdShofer() {
        return idShofer;
    }

    public void setIdShofer(String idShofer) {
        this.idShofer = idShofer;
    }


}
