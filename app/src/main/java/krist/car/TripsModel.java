package krist.car;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

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


    private String tripID;


    public TripsModel(){}

    public TripsModel(String idShofer,String vNisja, String vMberritja, String data, String ora, String vendet){

        this.idShofer = idShofer;
        this.vNisja = vNisja;
        this.vMberritja = vMberritja;
        this.data = data;
        this.ora = ora;
        this.vendet = vendet;


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
