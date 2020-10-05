package krist.car.search_trips.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import krist.car.R
import krist.car.models.TripsModel

open class TripsAdapterKt(val listener: OnTripClickedListener) : RecyclerView.Adapter<TripsAdapterKt.ViewHolder>() {
    private var dataSet = arrayListOf<TripsModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ketubehetcdogje, parent, false)
        return ViewHolder(view) {
            listener.onTripsClicked(dataSet.get(it))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet.get(position))
    }

    override fun getItemCount(): Int = dataSet.size

    fun setTrips(trips: ArrayList<TripsModel>) {
        this.dataSet = trips
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View, callback: (Int) -> Unit): RecyclerView.ViewHolder(itemView) {
        private var carImgView: ImageView? = null
        private var tripStartLocation: TextView? = null
        private var tripEndLocation: TextView? = null
        private var date: TextView? = null
        private var time: TextView? = null
        private var seats: TextView? = null

        init {
            carImgView = itemView.findViewById(R.id.foto)
            tripStartLocation = itemView.findViewById(R.id.new_nisja)
            tripEndLocation = itemView.findViewById(R.id.new_mberritja)
            date = itemView.findViewById(R.id.new_data)
            time = itemView.findViewById(R.id.new_ora)
            seats = itemView.findViewById(R.id.new_vendet)

            itemView.setOnClickListener { callback.invoke(adapterPosition) }
        }

        fun bind(model: TripsModel) {
            Picasso.get().load(model.uri).fit().centerCrop().into(carImgView)
            tripStartLocation!!.text = model.getvNisja()
            tripEndLocation!!.text = model.getvMberritja()
            date!!.text = model.data
            time!!.text = model.ora
            seats!!.text = model.vendet
        }
    }
}

interface OnTripClickedListener{
    fun onTripsClicked(model: TripsModel)
}