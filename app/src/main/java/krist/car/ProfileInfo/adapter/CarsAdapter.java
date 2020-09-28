package krist.car.ProfileInfo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import krist.car.ProfileInfo.Models.CarModel;
import krist.car.R;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ViewHolder> {
    private ArrayList<CarModel> cars;

    public CarsAdapter(ArrayList<CarModel> cars) {
        this.cars = cars;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_recycler_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CarModel car = cars.get(position);

        holder.carModel.setText(car.getCarModel());
        holder.carMake.setText(car.getCarMarks());
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView carMake;
        public TextView carModel;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            carMake = itemView.findViewById(R.id.car_make);
            carModel = itemView.findViewById(R.id.car_model);
        }
    }
}
