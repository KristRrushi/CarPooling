package krist.car.history.driver

import krist.car.models.PassToTripsModel

interface OnPostTripClickedListener {
    fun onTripClicked(passenger: ArrayList<PassToTripsModel>)
}