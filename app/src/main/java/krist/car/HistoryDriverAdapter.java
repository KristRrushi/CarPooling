package krist.car;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HistoryDriverAdapter extends RecyclerView.Adapter<HistoryDriverAdapter.ViewHolder> {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataTrips = database.getReference("trips");


    public  class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View list_item_view;
        public ImageView imageView;
        public TextView vNisja, vMberritja, data, ora,vendet;
        public Button list_button;
        FirebaseUser idauth;
        private FirebaseAuth mAuth;
        private StorageReference mStorageRef;

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference databaseUsers = database.getReference("users");

        DatabaseReference dataTrips = database.getReference("trips");




        public ViewHolder(View v) {

            super(v);

            list_item_view = v;

            imageView = list_item_view.findViewById(R.id.img_mak);
            vNisja = list_item_view.findViewById(R.id.new_nisja);
            vMberritja = list_item_view.findViewById(R.id.new_mberritja);
            data = list_item_view.findViewById(R.id.new_ora);
            ora = list_item_view.findViewById(R.id.new_data);
            vendet = list_item_view.findViewById(R.id.new_vendet);


            idauth = FirebaseAuth.getInstance().getCurrentUser();
            mStorageRef = FirebaseStorage.getInstance().getReference();




        }
    }


    private List<TripsModel> dataSet;
    private List<TripsModel> dataSetFiltered;


    public HistoryDriverAdapter(List<TripsModel> dataSet){
        this.dataSet = dataSet;
    }



    @NonNull
    @Override
    public HistoryDriverAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View trips_view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trips_list_item_2,parent,false);





        return new HistoryDriverAdapter.ViewHolder(trips_view);
    }


    public void deleteItem(TripsModel model){


        dataTrips.child(model.getTripID()).removeValue();

        //notifyItemRemoved(position);
        notifyDataSetChanged();
    }




    @Override
    public void onBindViewHolder(@NonNull final HistoryDriverAdapter.ViewHolder holder, final int position) {
        holder.vMberritja.setText(dataSet.get(position).getvMberritja());
        holder.vNisja.setText(dataSet.get(position).getvNisja());
        holder.data.setText(dataSet.get(position).getData());
        holder.ora.setText(dataSet.get(position).getOra());
        holder.vendet.setText(dataSet.get(position).getVendet());
        Picasso.get().load(dataSet.get(position).getUri()).fit().centerCrop().into(holder.imageView);

       // String id = dataSet.get(position).getTripID();





        holder.list_item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String tripId = dataSet.get(position).getTripID();



                Intent intent = new Intent(view.getContext(), PopUpDriver.class);

                Bundle bundle = new Bundle();

                bundle.putString("tripID", tripId);

                intent.putExtra("bundleDriver", bundle);


                view.getContext().startActivity(intent);













            }
        });






    }




    @Override
    public int getItemCount() {
        return dataSet.size();
    }





}
