package krist.car.history.passenger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import krist.car.R
import krist.car.models.TripsModel
import krist.car.search_trips.driver_info.BookTrips


class UserBookTripsAdapter(val listener: DriverListener): RecyclerView.Adapter<UserBookTripsAdapter.ViewHolder>() {
    private var dataSet = arrayListOf<BookTrips>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trips_list_item_2, parent, false)
        return ViewHolder(view) {
            val currentSelected = dataSet.get(it)
            val data = UserAndCarRefLink(currentSelected.idShofer, currentSelected.carRef)
            listener.getDriverId(data)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet.get(position))
    }

    override fun getItemCount(): Int = dataSet.size

    fun setTrips(passenger: ArrayList<BookTrips>) {
        this.dataSet = passenger
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View, callback: (Int) -> Unit): RecyclerView.ViewHolder(itemView) {
        private var startLocation: TextView = itemView.findViewById(R.id.new_nisja)
        private var endLocation: TextView = itemView.findViewById(R.id.new_mberritja)
        private var date: TextView = itemView.findViewById(R.id.new_data)
        private var time: TextView = itemView.findViewById(R.id.new_ora)

        init {
            itemView.setOnClickListener { callback.invoke(adapterPosition) }
        }

        fun bind(model: BookTrips) {
            startLocation.text = model.vNisja
            endLocation.text = model.vMberritja
            date.text = model.data
            time.text = model.ora
        }
    }
}

interface DriverListener {
    fun  getDriverId(data: UserAndCarRefLink)
}

data class UserAndCarRefLink(
        var userId: String = "",
        var carId: String = ""
)