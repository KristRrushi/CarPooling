package krist.car;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import krist.car.Models.TripsModel;

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
        private TextView vendetkrist;

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference databaseUsers = database.getReference("users");

        DatabaseReference dataTrips = database.getReference("trips");

        public RelativeLayout viewBackground;
        public LinearLayout viewForeground;




        public ViewHolder(View v) {

            super(v);

            list_item_view = v;

           // imageView = list_item_view.findViewById(R.id.img_mak);
            vNisja = list_item_view.findViewById(R.id.new_nisja);
            vMberritja = list_item_view.findViewById(R.id.new_mberritja);
            data = list_item_view.findViewById(R.id.new_ora);
            ora = list_item_view.findViewById(R.id.new_data);
            //vendet = list_item_view.findViewById(R.id.new_vendet);

           // vendetkrist = list_item_view.findViewById(R.id.krist_vendet);

            viewBackground = list_item_view.findViewById(R.id.view_background);
            viewForeground = list_item_view.findViewById(R.id.view_foreground);



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
                .inflate(R.layout.list_vod_layout_item,parent,false);





        return new HistoryDriverAdapter.ViewHolder(trips_view);
    }






    @Override
    public void onBindViewHolder(@NonNull final HistoryDriverAdapter.ViewHolder holder, final int position) {
        holder.vMberritja.setText(dataSet.get(position).getvMberritja());
        holder.vNisja.setText(dataSet.get(position).getvNisja());
        holder.data.setText(dataSet.get(position).getData());
        holder.ora.setText(dataSet.get(position).getOra());
        //holder.vendet.setText(dataSet.get(position).getVendet());
        //Picasso.get().load(dataSet.get(position).getUri()).fit().centerCrop().into(holder.imageView);






        String id = dataSet.get(position).getTripID();


        DatabaseReference databaseReference = database.getReference("trips").child(id).child("passengers");



        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("Pasagjeret : " + dataSnapshot.getChildrenCount());

                if(dataSnapshot.getChildrenCount() == 0){



                }else {

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







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




    public void removeItem(int position){

        String id = dataSet.get(position).getTripID();
        dataTrips.child(id).setValue(null);

        dataSet.remove(position);
        notifyItemRemoved(position);

    }


}
