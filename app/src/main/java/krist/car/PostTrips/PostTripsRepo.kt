package krist.car.PostTrips

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import krist.car.Models.TripsModel
import krist.car.ProfileInfo.Models.CarModel
import krist.car.core.BaseRepo

class PostTripsRepo() : BaseRepo(){
    fun postTrip(trip: TripsModel): MutableLiveData<Boolean> {
        val isTripPostedSuccessfully = MutableLiveData<Boolean>()

        trip.idShofer = userId

        getUserCarSelectedImg {
            trip.uri = it
            api!!.getDatebaseReferenceToThisEndPoint("trips").push().setValue(trip).addOnCompleteListener { task ->
                isTripPostedSuccessfully.value = task.isSuccessful
            }
        }
        return isTripPostedSuccessfully
    }

    fun getUserCarSelectedImg(imgRef: (String) -> Unit) {
        val userId = api!!.userUId

        api.getDatebaseReferenceToThisEndPoint("user_cars").child(userId).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val data = p0.getValue(UserCarsModel::class.java)
                data?.let {
                    val carSelectedRef = retrieveCarImg(it)
                    imgRef.invoke(carSelectedRef)
                }
            }
            override fun onCancelled(p0: DatabaseError) {
                //Catch errors
            }
        })
    }

    fun checkIfUserHaveRegisterCar(): MutableLiveData<Boolean> {
        val isCarRegister = MutableLiveData<Boolean>()

        api!!.getDatebaseReferenceToThisEndPoint("user_cars").child(userId).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                isCarRegister.value = p0.value != null
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

        return isCarRegister
    }

    private fun retrieveCarImg(model: UserCarsModel): String {
        var carSelected = ""

        model.cars.forEach { car ->
            if(model.selected_car == car.key) {
                carSelected = car.value.carImgRef
            }
        }
        return carSelected
    }
}

data class UserCarsModel(var cars: HashMap<String,CarModel> = hashMapOf(), var selected_car: String = "")