package krist.car.search_trips

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import krist.car.core.BaseRepo
import krist.car.models.TripsModel

class SearchTripsRepo: BaseRepo() {
    fun getAllTrips(): MutableLiveData<ArrayList<TripsModel>> {
        val trips = MutableLiveData<ArrayList<TripsModel>>()
        val tripsS = ArrayList<TripsModel>()

        api!!.getDatebaseReferenceToThisEndPoint("trips").addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val tripsModel = p0.getValue(TripsModel::class.java)
                tripsModel?.tripID = p0.key

                tripsModel?.let { tripsS.add(it) }

                trips.value = tripsS
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        return  trips
    }
}