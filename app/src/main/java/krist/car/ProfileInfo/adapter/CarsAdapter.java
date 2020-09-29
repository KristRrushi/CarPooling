package krist.car.ProfileInfo.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


import krist.car.ProfileInfo.Models.CarModel;
import krist.car.R;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ViewHolder> implements CarPositionListener {
    private ArrayList<CarModel> cars;
    private CarSelectedListener listener;

    public CarsAdapter(ArrayList<CarModel> cars, CarSelectedListener listener) {
        this.cars = cars;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_recycler_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CarModel car = cars.get(position);
        holder.bind(car);
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    @Override
    public void carPosition(int position) {
        String carRef = cars.get(position).getCarKey();
        listener.onCarSelected(carRef);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView carMake;
        TextView carModel;
        ImageView carImg;
        Button selectCarButton;
        CarPositionListener listener;


        ViewHolder(@NonNull View itemView, CarPositionListener listener) {
            super(itemView);

            this.listener = listener;

            carImg = itemView.findViewById(R.id.car_img);
            carMake = itemView.findViewById(R.id.car_make);
            carModel = itemView.findViewById(R.id.car_model);
            selectCarButton = itemView.findViewById(R.id.select_car_button);

            selectCarButton.setOnClickListener( v -> {
                listener.carPosition(getAdapterPosition());
            });
        }

        void bind(CarModel model) {
            carMake.setText(model.getCarMarks());
            carModel.setText(model.getCarModel());
            Picasso.get().load(model.getCarImgRef()).fit().centerCrop().into(carImg);
        }
    }
}