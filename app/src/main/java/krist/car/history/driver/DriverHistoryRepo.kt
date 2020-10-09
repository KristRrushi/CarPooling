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
        api!!.getDatebaseReferenceToThisEndPoint("trips").addValueEventListener(object : ValueEventListener{
            var tripss = ArrayList<TripsModel>()

            override fun onDataChange(p0: DataSnapshot) {
                for(data: DataSnapshot in p0.children) {
                    val pT = data.getValue(TripsModel::class.java)
                    if(pT!!.idShofer == userId) {
                        pT.tripID = p0.key
                        tripss.add(pT)
                    }
                }
                trips.value = tripss
                tripss = arrayListOf()
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        return trips
    }
}

