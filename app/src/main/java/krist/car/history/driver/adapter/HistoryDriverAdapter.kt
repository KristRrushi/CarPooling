package krist.car.history.driver.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import krist.car.R
import krist.car.history.driver.OnPostTripClickedListener
import krist.car.history.driver.PopUpDriver
import krist.car.models.PassToTripsModel
import krist.car.models.TripsModel

class HistoryDriverAdapter(val listener: OnPostTripClickedListener) : RecyclerView.Adapter<HistoryDriverAdapter.ViewHolder>() {
    private var dataSet: ArrayList<TripsModel> = arrayListOf()

    fun setTrips(postedTrips: ArrayList<TripsModel>) {
        dataSet = postedTrips
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_vod_layout_item, parent, false)
        return ViewHolder(view) {
            val passenger = arrayListOf<PassToTripsModel>()
            dataSet.get(it).passengers?.apply {
                for((k, v) in this) {
                    passenger.add(v)
                }
                listener.onTripClicked(passenger)
            }

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet.get(position))
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun removeItem(position: Int) {
        val id = dataSet[position].tripID
        dataSet.removeAt(position)

        notifyItemRemoved(position)
    }

    inner class ViewHolder( itemView : View, callback: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private var vNisja: TextView? = null
        private var vMberritja: TextView? = null
        private var data: TextView? = null
        private var ora: TextView? = null
        private var viewBackground: RelativeLayout? = null
        private var viewForeground: LinearLayout? = null

        init {
            vNisja = itemView.findViewById(R.id.new_nisja)
            vMberritja = itemView.findViewById(R.id.new_mberritja)
            data = itemView.findViewById(R.id.new_ora)
            ora = itemView.findViewById(R.id.new_data)
            viewBackground = itemView.findViewById(R.id.view_background)
            viewForeground = itemView.findViewById(R.id.view_foreground)

            itemView.setOnClickListener { callback.invoke(adapterPosition) }
        }

        fun bind (tripModel: TripsModel) {
            vNisja!!.setText(tripModel.getvNisja())
            vMberritja!!.setText(tripModel.getvMberritja())
            data!!.setText(tripModel.data)
            ora!!.setText(tripModel.ora)
        }
    }
}

