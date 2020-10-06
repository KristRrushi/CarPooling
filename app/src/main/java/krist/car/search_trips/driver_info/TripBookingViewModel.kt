package krist.car.search_trips.driver_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import krist.car.models.UserModel
import krist.car.profile_info.Models.CarModel
import krist.car.profile_info.UserInfo

class TripBookingViewModel(private val repo: TripBookingInfoRepo = TripBookingInfoRepo()) : ViewModel() {
    private var _userInfo = MutableLiveData<UserModel>()
    private var _carInfo = MutableLiveData<CarModel>()
    private var _isBookingFlowSuccess = MutableLiveData<Boolean>()

    fun getDriverInfoBaseOnId(driverId: String) = run { _userInfo = repo.getDriverInfo(driverId) }
    fun getDriverSelectedCar(driverId: String) = run { _carInfo = repo.getDriverCar(driverId)}
    fun bookTrip(tripId: String) = run { _isBookingFlowSuccess = repo.bookTrip(tripId)}

    fun driverInfo(): LiveData<UserModel> = _userInfo
    fun driverCar(): LiveData<CarModel> = _carInfo
    fun isBookingFlowSuccess() : LiveData<Boolean> = _isBookingFlowSuccess
}