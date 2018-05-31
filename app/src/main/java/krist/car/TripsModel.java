package krist.car;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pampers on 12/19/2017.
 */


public class TripsModel {


    private String vNisja;
    private String vMberritja;
    private  String data;
    private   String ora;
    private String vendet;
    private String idShofer;


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

    public String getIdShofer() {
        return idShofer;
    }


}
