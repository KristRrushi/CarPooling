package krist.car.history.driver

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import krist.car.core.BaseRepo
import krist.car.models.TripsModel

class DriverHistoryRepo: BaseRepo() {
    fun getTripsPostedByUser(): MutableLiveData<ArrayList<TripsModel>> {
        val trips = MutableLiveData<ArrayList<TripsModel>>()
        api!!.getDatebaseReferenceToThisEndPoint("trips").addChildEventListener(object : ChildEventListener{
            val tripss = ArrayList<TripsModel>()
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val pT = p0.getValue(TripsModel::class.java)
                if(pT!!.idShofer == userId) {
                    pT.tripID = p0.key
                    tripss.add(pT)
                }
                trips.value = tripss
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val pT = p0.getValue(TripsModel::class.java)
                if(pT!!.idShofer == userId) {
                    tripss.add(pT)
                }

                trips.value = tripss
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                val pT = p0.getValue(TripsModel::class.java)
                if(pT!!.idShofer == userId) {
                    tripss.add(pT)
                }

                trips.value = tripss
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        return trips
    }
}

