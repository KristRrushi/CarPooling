package krist.car.history.passenger

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.driver_car_info_bottomsheet_layout.*
import kotlinx.android.synthetic.main.pop_up_main_final.*
import krist.car.R


class DriverGeneralInfoBottomsheet(private val info: DriverInfoCarInfo, private val ratingStarCallback: RatingStartListener): BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       return inflater.inflate(R.layout.driver_car_info_bottomsheet_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Picasso.get().load(info.carImg).fit().centerCrop().into(car_img)
        Picasso.get().load(info.driverImgRef).fit().centerCrop().into(driver_img)
        driver_name.text = info.driverName
        driver_age.text = info.driverAge
        driver_gener.text = info.driverGener
        driver_phone.text = info.driverPhone

        setupRatingStart()
    }

    override fun onStart() {
        super.onStart()

        dialog?.let {
            val view = it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            view.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }

        view?.let {
           it.post {
               val parent = it.parent as View
               val params: CoordinatorLayout.LayoutParams = parent.layoutParams as CoordinatorLayout.LayoutParams
               val behavior = params.behavior as BottomSheetBehavior
               behavior.peekHeight = 1000
           }
        }
    }

    private fun setupRatingStart() {
        driver_rating_bar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            ratingStarCallback.onRatingStartClicked(ratingBar.rating, info.driverId)
        }
    }
}

interface RatingStartListener {
    fun onRatingStartClicked(rating: Float, driverID: String)
}