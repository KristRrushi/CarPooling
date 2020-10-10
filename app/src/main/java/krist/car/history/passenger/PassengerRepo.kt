package krist.car.history.passenger

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import krist.car.core.BaseRepo
import krist.car.models.TripsModel
import krist.car.models.UserModel
import krist.car.profile_info.Models.CarModel
import krist.car.profile_info.UserInfo
import krist.car.search_trips.driver_info.BookTrips

class PassengerRepo : BaseRepo() {
    fun getUserBookedTrips() :  MutableLiveData<ArrayList<BookTrips>> {
        val trips = MutableLiveData<ArrayList<BookTrips>>()
        api!!.getDatebaseReferenceToThisEndPoint("users").child(userId).child("booked_trips").addValueEventListener(object : ValueEventListener {
            var tripss = arrayListOf<BookTrips>()

            override fun onDataChange(p0: DataSnapshot) {
                for (data : DataSnapshot in p0.children) {
                    val t = data.getValue(BookTrips::class.java)
                    t?.let { tripss.add(it) }
                }
                trips.value = tripss
                tripss = arrayListOf()
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
        return trips
    }

    fun rateDriver(rate: Int, driverId: String) : MutableLiveData<Boolean> {
        val isSuccess = MutableLiveData<Boolean>()

        api!!.getDatebaseReferenceToThisEndPoint("users").child(driverId).child("rating").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val currentRate = p0.getValue(RatingModel::class.java)
                currentRate?.let {
                    rateParser(driverId, rate, it){
                        isSuccess.value = it
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) { }
        })
        return isSuccess
    }

    private fun rateParser(driverId: String, rate: Int, ratingModel: RatingModel, isSuccess: (Boolean) -> Unit) {
        val updateRating: HashMap<String, Any> = hashMapOf()
        when(rate) {
            0 -> updateRating["zero_star"] = ratingModel.zero_star + 1
            1 -> updateRating["one_star"] = ratingModel.one_star + 1
            2 -> updateRating["two_star"] = ratingModel.two_star + 1
            3 -> updateRating["three_star"] = ratingModel.three_star + 1
            4 -> updateRating["four_star"] = ratingModel.four_star + 1
            5 -> updateRating["five_star"] = ratingModel.five_star + 1
        }

        api!!.getDatebaseReferenceToThisEndPoint("users").child(driverId).child("rating").updateChildren(updateRating).addOnCompleteListener {
            isSuccess.invoke(it.isSuccessful)
        }
    }

    fun getGeneralDriverInfo(driverId: String, carRef: String): MutableLiveData<DriverInfoCarInfo>{
        val userGeneralInfo = MutableLiveData<DriverInfoCarInfo>()
        getDriverInfo(driverId) {generalInfo ->
            api!!.getDatebaseReferenceToThisEndPoint("user_cars").child(driverId).child("cars").child(carRef).addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    val data = p0.getValue(CarModel::class.java)
                    data?.let { car ->
                        generalInfo.carMake = car.carMarks
                        generalInfo.carModel = car.carModel
                        generalInfo.carPlate = car.carPlate
                        generalInfo.carImg = car.carImgRef

                        userGeneralInfo.value = generalInfo
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                    Log.d("lol", p0.message + " from Passenger Repo")
                }
            })
        }

        return userGeneralInfo
    }

    private fun getDriverInfo(driverId: String, driverInfoCallback: (DriverInfoCarInfo) -> Unit) {
        api!!.getDatebaseReferenceToThisEndPoint("users").child(driverId).addListenerForSingleValueEvent(object : ValueEventListener{
            val generalInfo = DriverInfoCarInfo()
            override fun onDataChange(p0: DataSnapshot) {
                val data = p0.getValue(UserModel::class.java)
                data?.let {
                    generalInfo.driverId = it.id
                    generalInfo.driverName = it.emri
                    generalInfo.driverAge = it.birthday
                    generalInfo.driverGener = it.gener
                    generalInfo.driverPhone = it.phone
                    generalInfo.driverImgRef = it.userImgRef

                    driverInfoCallback.invoke(generalInfo)
                }

            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d("lol", p0.message + " from Passenger Repo")
            }
        })

    }
}

data class DriverInfoCarInfo(
        var driverId: String = "",
        var driverName: String = "",
        var driverAge: String = "",
        var driverGener: String = "",
        var driverPhone: String = "",
        var driverImgRef: String = "",
        var carMake: String = "",
        var carModel: String = "",
        var carPlate: String = "",
        var carImg: String = ""
)

data class RatingModel(
        var zero_star: Int = 0,
        var one_star: Int = 0,
        var two_star: Int = 0,
        var three_star: Int = 0,
        var four_star: Int = 0,
        var five_star: Int = 0
)