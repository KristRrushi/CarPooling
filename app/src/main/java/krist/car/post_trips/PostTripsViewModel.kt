package krist.car.post_trips

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import krist.car.models.TripsModel

class PostTripsViewModel(private val repo: PostTripsRepo = PostTripsRepo()): ViewModel() {
    private var isTripPostedSuccessfully = MutableLiveData<Boolean>()
    private var doesCarExist = MutableLiveData<Boolean>()

    fun isTripPosted(): LiveData<Boolean> = isTripPostedSuccessfully

    fun postTrip(tripsModel: TripsModel) { isTripPostedSuccessfully = repo.postTrip(tripsModel) }

    fun doesUserHaveCar(): LiveData<Boolean> = doesCarExist

    fun checkIfUserHaveCar() { doesCarExist = repo.checkIfUserHaveRegisterCar() }
}