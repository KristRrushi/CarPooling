package krist.car.history.driver

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import krist.car.models.PassToTripsModel
import krist.car.models.TripsModel

class DriverHistoryViewModel(private val repo: DriverHistoryRepo = DriverHistoryRepo()): ViewModel() {
    private var _trips = MutableLiveData<ArrayList<TripsModel>>()
    private var _passengers = MutableLiveData<ArrayList<PassToTripsModel>>()

    fun getPostedTrips() : LiveData<ArrayList<TripsModel>> = _trips
    fun getPassengers() : LiveData<ArrayList<PassToTripsModel>> = _passengers

    fun getTrips() { _trips = repo.getTripsPostedByUser() }

    fun getPassengersBaseOnTripId(tripsId: String) {
        var passengers = arrayListOf<PassToTripsModel>()
        val t = _trips.value

        for(i in t!!.indices) {
            if(!t.get(i).passengers.isNullOrEmpty()) {
                val passe = t.get(i).passengers
                for((_, value) in passe) {
                    passengers.add(value)
                }
                _passengers.value = passengers
            }
        }
    }

}