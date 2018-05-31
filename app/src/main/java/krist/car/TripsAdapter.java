package krist.car;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tagolli on 5/31/18.
 */

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View list_item_view;
        public ImageView imageView;
        public TextView vNisja, vMberritja, data, ora;

        public ViewHolder(View v) {

            super(v);

            list_item_view = v;

            imageView = list_item_view.findViewById(R.id.imageView);
            vNisja = list_item_view.findViewById(R.id.trips_txt_nisja);
            vMberritja = list_item_view.findViewById(R.id.trips_txt_mberritja);
            data = list_item_view.findViewById(R.id.trips_txt_data);
            ora = list_item_view.findViewById(R.id.trips_txt_ora);
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
    public void onBindViewHolder(@NonNull final TripsAdapter.ViewHolder holder, int position) {
        holder.vMberritja.setText(dataSet.get(position).getvMberritja());
        holder.vNisja.setText(dataSet.get(position).getvNisja());
        holder.data.setText(dataSet.get(position).getData());
        holder.ora.setText(dataSet.get(position).getOra());

        holder.list_item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Test",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
