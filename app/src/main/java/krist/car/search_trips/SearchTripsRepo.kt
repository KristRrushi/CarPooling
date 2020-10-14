package krist.car.search_trips

import android.util.Log
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
        var tripsS = ArrayList<TripsModel>()

        api!!.getDatebaseReferenceToThisEndPoint("trips").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                for(data: DataSnapshot in p0.children) {
                   val tripModel = data.getValue(TripsModel::class.java)
                   tripModel?.tripID = data.key
                   tripModel?.let { tripsS.add(it) }
               }

                trips.value = tripsS
                tripsS = arrayListOf()
            }

            override fun onCancelled(p0: DatabaseError) { }

        })

        return  trips
    }
}