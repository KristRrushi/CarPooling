package krist.car;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PopUpDriverAdapter extends BaseAdapter{

    Context c;
    ArrayList<PassToTrips> dataSet;


    public PopUpDriverAdapter(Context c, ArrayList<PassToTrips> dataSet) {
        this.c = c;
        this.dataSet = dataSet;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Object getItem(int i) {
        return dataSet.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){

            view = LayoutInflater.from(c).inflate(R.layout.item_list_pop_up_driver,viewGroup,false);
        }


        TextView name = (TextView) view.findViewById(R.id.emri_pop_up_driver);
        TextView phone = (TextView) view.findViewById(R.id.tel_pop_up_driver);

        final PassToTrips passToTrips = (PassToTrips) this.getItem(i);

        name.setText(passToTrips.getEmri());
        phone.setText(passToTrips.getPhone());



        return view;
    }
}