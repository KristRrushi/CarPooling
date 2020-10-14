package krist.car.profile_info.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.squareup.picasso.Picasso
import krist.car.R
import krist.car.profile_info.Models.CarModel
import java.util.*

class CarsAdapter(private val cars: ArrayList<CarModel> = arrayListOf(), private val listener: CarSelectedListener) : RecyclerView.Adapter<CarsAdapter.ViewHolder>(), CarPositionListener {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_recycler_item, parent, false)
        return ViewHolder(view, this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cars.get(position).let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    override fun carPosition(position: Int) {
        val carRef = cars[position].carKey
        listener.onCarSelected(carRef)
    }

    inner class ViewHolder(itemView: View, var listener: CarPositionListener) : RecyclerView.ViewHolder(itemView) {
        var carMake: TextView
        var carModel: TextView
        var carImg: ImageView
        var selectCarButton: Button

        fun bind(model: CarModel) {
            carMake.text = model.carMarks
            carModel.text = model.carModel
            carImg.load(model.carImgRef) {
                crossfade(true)
            }
        }

        init {
            carImg = itemView.findViewById(R.id.car_img)
            carMake = itemView.findViewById(R.id.car_make)
            carModel = itemView.findViewById(R.id.car_model)
            selectCarButton = itemView.findViewById(R.id.select_car_button)
            selectCarButton.setOnClickListener { v: View? -> listener.carPosition(adapterPosition) }
        }
    }
}