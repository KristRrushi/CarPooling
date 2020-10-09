package krist.car.history.driver

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import krist.car.R
import krist.car.history.driver.adapter.PassengerListAdapter
import krist.car.history.driver.adapter.PopUpDriverAdapter
import krist.car.models.PassToTripsModel

class PassengerListBottomSheetDialog(private val passenger: ArrayList<PassToTripsModel>): BottomSheetDialogFragment() {
    private lateinit var passengerList: RecyclerView
    private var adapter: PassengerListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.passenger_bottomsheet_layout, container, false)

        passengerList = view.findViewById(R.id.passenger_list)
        passengerList.apply {
           layoutManager = LinearLayoutManager(context)
        }
        adapter = PassengerListAdapter()
        passengerList.adapter = adapter
        adapter?.setPassenger(passenger)
        return view
    }
}