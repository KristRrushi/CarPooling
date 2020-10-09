package krist.car.history.adapter;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import krist.car.R;
import krist.car.history.PopUpCoDriver;
import krist.car.models.TripsModel;

import static android.content.ContentValues.TAG;

public class CoDriverAdapter extends RecyclerView.Adapter<CoDriverAdapter.ViewHolder> {


    private List<TripsModel> dataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View list_item_view;
        public ImageView imageView;
        public TextView vNisja, vMberritja, data, ora, vendet;
        public Button list_button;
        FirebaseUser idauth;
        private FirebaseAuth mAuth;
        private StorageReference mStorageRef;

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference databaseUsers = database.getReference("users");





        public ViewHolder(View v) {

            super(v);

            list_item_view = v;

           // imageView = list_item_view.findViewById(R.id.img_mak);
            vNisja = list_item_view.findViewById(R.id.new_nisja);
            vMberritja = list_item_view.findViewById(R.id.new_mberritja);
            data = list_item_view.findViewById(R.id.new_ora);
            ora = list_item_view.findViewById(R.id.new_data);
           // vendet = list_item_view.findViewById(R.id.new_vendet);



            idauth = FirebaseAuth.getInstance().getCurrentUser();
            mStorageRef = FirebaseStorage.getInstance().getReference();


        }
    }

    public CoDriverAdapter(){};










    @NonNull
    @Override
    public CoDriverAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View trips_view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trips_list_item_2,parent,false);



        return new CoDriverAdapter.ViewHolder(trips_view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoDriverAdapter.ViewHolder holder, final int position) {

        holder.vMberritja.setText(dataSet.get(position).getvMberritja());
        holder.vNisja.setText(dataSet.get(position).getvNisja());
        holder.data.setText(dataSet.get(position).getData());
        holder.ora.setText(dataSet.get(position).getOra());
       // holder.vendet.setText(dataSet.get(position).getVendet());
        //Picasso.get().load(dataSet.get(position).getUri()).fit().centerCrop().into(holder.imageView);




        holder.list_item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String shoferId = dataSet.get(position).getIdShofer();
                String tripId = dataSet.get(position).getTripID();
                Intent myIntent = new Intent(view.getContext(), PopUpCoDriver.class);


                System.out.println(shoferId);



                Bundle bundle = new Bundle();
                bundle.putString("shoferID2", shoferId);
                bundle.putString("tripID2", tripId);
                myIntent.putExtra("codriver",bundle);


                Log.v(TAG, "Kalimi id");
                Log.v(TAG, shoferId);


                view.getContext().startActivity(myIntent);
            }
        });











    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
