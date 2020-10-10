package krist.car.history.passenger

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import krist.car.models.TripsModel
import krist.car.search_trips.driver_info.BookTrips

class PassengerViewModel(private val repo: PassengerRepo = PassengerRepo()): ViewModel() {
    private var _userBookedTrips = MutableLiveData<ArrayList<BookTrips>>()
    private var _driveInfo = MutableLiveData<DriverInfoCarInfo>()
    private var _driverRating = MutableLiveData<Boolean>()

    fun bookedTrips() = _userBookedTrips
    fun userGeneralInfo() = _driveInfo
    fun isDriverRatedSuccesfully() = _driverRating

    fun getUserBookedTrips() { _userBookedTrips = repo.getUserBookedTrips() }
    fun getUserInfo(driverId: String, driverCarRef: String) { _driveInfo = repo.getGeneralDriverInfo(driverId, driverCarRef)}
    fun rateDriver(rating: Int, driverId: String) { _driverRating = repo.rateDriver(rating, driverId) }
}