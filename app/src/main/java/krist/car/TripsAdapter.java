package krist.car;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by tagolli on 5/31/18.
 */

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.ViewHolder> {


    public  class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View list_item_view;
        public ImageView imageView;
        public TextView vNisja, vMberritja, data, ora;
        public Button list_button;
        FirebaseUser idauth;



        public ViewHolder(View v) {

            super(v);

            list_item_view = v;

            imageView = list_item_view.findViewById(R.id.imageView);
            vNisja = list_item_view.findViewById(R.id.trips_txt_nisja);
            vMberritja = list_item_view.findViewById(R.id.trips_txt_mberritja);
            data = list_item_view.findViewById(R.id.trips_txt_data);
            ora = list_item_view.findViewById(R.id.trips_txt_ora);
            list_button = list_item_view.findViewById(R.id.list_button);
            idauth = FirebaseAuth.getInstance().getCurrentUser();

            list_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final String id = idauth.getUid();

                    Toast.makeText(view.getContext(),id,Toast.LENGTH_SHORT).show();









                }
            });
        }
    }


    private List<TripsModel> dataSet;

    public TripsAdapter(List<TripsModel> dataSet){
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public TripsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View trips_view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.trips_list_item,parent,false);



        return new ViewHolder(trips_view);
    }




    @Override
    public void onBindViewHolder(@NonNull final TripsAdapter.ViewHolder holder, final int position) {
        holder.vMberritja.setText(dataSet.get(position).getvMberritja());
        holder.vNisja.setText(dataSet.get(position).getvNisja());
        holder.data.setText(dataSet.get(position).getData());
        holder.ora.setText(dataSet.get(position).getOra());


        holder.list_item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), dataSet.get(position).getTripID(), Toast.LENGTH_SHORT).show();
                //Krijo Dialog per tripID kalo te trpi  id e usert qe e zgjedh ///
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
