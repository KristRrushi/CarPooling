package krist.car.history.driver.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import krist.car.R
import krist.car.models.PassToTripsModel

class PassengerListAdapter: RecyclerView.Adapter<PassengerListAdapter.ViewHolder>() {
    private var dataSet = arrayListOf<PassToTripsModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_pop_up_driver, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet.get(position))
    }

    override fun getItemCount(): Int = dataSet.size

    fun setPassenger(passenger: ArrayList<PassToTripsModel>) {
        this.dataSet = passenger
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var phone: TextView
        var age: TextView
        var gener: TextView
        var passengerImg: ImageView

        init {
            name = itemView.findViewById(R.id.emri_pop_up_driver)
            phone = itemView.findViewById(R.id.tel_pop_up_driver)
            age = itemView.findViewById(R.id.mosha_pop_up_driver)
            gener = itemView.findViewById(R.id.gjinia_pop_up_driver)
            passengerImg = itemView.findViewById(R.id.foto_pop_up_driver)
        }

        fun bind(model: PassToTripsModel) {
            name.text = model.emri
            phone.text = model.phone
            age.text = model.mosha
            gener.text = model.gjinia
            Picasso.get().load(model.imageUrl).fit().centerCrop().into(passengerImg)
        }
    }
}