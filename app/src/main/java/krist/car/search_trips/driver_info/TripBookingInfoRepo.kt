package krist.car.search_trips.driver_info

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import krist.car.core.BaseRepo
import krist.car.models.PassToTripsModel
import krist.car.models.TripsModel
import krist.car.models.UserModel
import krist.car.profile_info.Models.CarModel
import krist.car.profile_info.Models.ProfileInfoModel
import krist.car.profile_info.UserInfo

class TripBookingInfoRepo: BaseRepo() {
    fun getDriverInfo(driverID: String): MutableLiveData<UserModel> {
        val driverInfo = MutableLiveData<UserModel>()
        api!!.getDatebaseReferenceToThisEndPoint("users").child(driverID).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val userInfo = p0.getValue(UserModel::class.java)
                driverInfo.value = userInfo
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        return driverInfo
    }

    fun getDriverCar(driverID: String) : MutableLiveData<CarModel> {
        val selectedCar = MutableLiveData<CarModel>()
        api!!.getDatebaseReferenceToThisEndPoint("user_cars").child(driverID).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val userCars = p0.getValue(UserCarsModel::class.java)

               for((id , car) in userCars!!.cars) {
                   if(id == userCars.selected_car) {
                       selectedCar.value = car
                   }
               }

            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return selectedCar
    }

    fun bookTrip(tripId: String) : MutableLiveData<Boolean>{
        val isSuccess = MutableLiveData<Boolean>()
        api!!.getDatebaseReferenceToThisEndPoint("trips").child(tripId).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val tripModel = p0.getValue(TripsModel::class.java)
                tripModel?.let { attachTripToCurrentUser(it) {
                        registerPassengerToTrip(tripId)
                        handleSeats(tripId, tripModel)
                        isSuccess.value = it
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return isSuccess
    }

    private fun attachTripToCurrentUser(trip: TripsModel, isSuccess: (Boolean) -> Unit) {
        val bookTrip = BookTrips(trip.cmimi, trip.idShofer, trip.ora, trip.data, trip.getvNisja(), trip.getvMberritja())
        api!!.getDatebaseReferenceToThisEndPoint("users").child(userId).child("booked_trips").push().setValue(bookTrip).addOnCompleteListener {
            isSuccess.invoke(it.isSuccessful)
        }
    }

    private fun handleSeats(tripId: String, trip: TripsModel) {
        var availableSeats = Integer.parseInt(trip.vendet)
        availableSeats--
        val updateSeatsMap = HashMap<String, Any>()
        updateSeatsMap.put("vendet", availableSeats.toString())

        api!!.getDatebaseReferenceToThisEndPoint("trips").child(tripId).updateChildren(updateSeatsMap)
    }

    private fun registerPassengerToTrip(tripId: String) {
        api!!.getDatebaseReferenceToThisEndPoint("users").child(userId).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val userInfo = p0.getValue(ProfileInfoModel::class.java)

                userInfo?.let {
                    val model = PassToTripsModel(it.emri, it.phone, it.birthday, it.gener, it.userImgRef)
                    api.getDatebaseReferenceToThisEndPoint("trips").child(tripId).child("passengers").child(userId).push().setValue(model)
                }

            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}

data class UserCarsModel(
        var selected_car: String = "",
        var cars : HashMap<String, CarModel> = hashMapOf()
)

data class BookTrips(
        var cmimi: String = "",
        var idShofer: String = "",
        var ora: String = "",
        var data: String = "",
        var vNisja: String = "",
        var vMberritja: String = ""
)