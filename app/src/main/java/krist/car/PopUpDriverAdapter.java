package krist.car;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import krist.car.models.PopUpDriverListModel;

public class PopUpDriverAdapter extends BaseAdapter{

    Context c;
    ArrayList<PopUpDriverListModel> dataSet;


    public PopUpDriverAdapter(Context c, ArrayList<PopUpDriverListModel> dataSet) {
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
        TextView mosha = (TextView) view.findViewById(R.id.mosha_pop_up_driver);
        TextView gjinia = (TextView) view.findViewById(R.id.gjinia_pop_up_driver);
        ImageView foto = (ImageView) view.findViewById(R.id.foto_pop_up_driver);




        final  PopUpDriverListModel popUpDriverListModel = (PopUpDriverListModel) this.getItem(i);

        name.setText(popUpDriverListModel.getEmri());
        phone.setText(popUpDriverListModel.getPhone());
        mosha.setText(popUpDriverListModel.getMosha());
        gjinia.setText(popUpDriverListModel.getGjinia());
        Picasso.get().load(popUpDriverListModel.getImageUrl()).fit().centerCrop().into(foto);




        return view;
    }
}