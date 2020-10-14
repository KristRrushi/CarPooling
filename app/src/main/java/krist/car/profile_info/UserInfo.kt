package krist.car.profile_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_cars_info_layout.*
import krist.car.R
import krist.car.car_register.DetajeActivity
import krist.car.profile_info.Models.CarModel
import krist.car.profile_info.Models.ProfileInfoModel
import krist.car.profile_info.adapter.CarSelectedListener
import krist.car.profile_info.adapter.CarsAdapter
import krist.car.utils.Helpers
import java.util.*

class UserInfo : Fragment(), CarSelectedListener {
    private var userImg: ImageView? = null
    private var nameTV: TextView? = null
    private var phoneTV: TextView? = null
    private var ageTV: TextView? = null
    private var generTV: TextView? = null
    private var userCarList: RecyclerView? = null
    private var viewModel: ProfileInfoViewModel? = null
    private var addCarButton: ImageView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.user_cars_info_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userImg = view.findViewById(R.id.user_info_img)
        nameTV = view.findViewById(R.id.user_info_name)
        phoneTV = view.findViewById(R.id.user_info_phone)
        ageTV = view.findViewById(R.id.user_info_age)
        generTV = view.findViewById(R.id.user_info_gener)
        userCarList = view.findViewById(R.id.user_info_car_list)
        addCarButton = view.findViewById(R.id.user_info_add_car)

        user_info_add_car.setOnClickListener {
            Helpers.goToActivity(context, DetajeActivity::class.java)
        }

        setupCarRecyclerView()
        setupUserProfileViewModel()
        userProfileDetails
        userCars
    }

    private fun setupUserProfileViewModel() {
        viewModel = ViewModelProvider(this).get(ProfileInfoViewModel::class.java)
    }

    private val userProfileDetails: Unit
        get() {
            viewModel!!.getUserProfile()
            viewModel!!.userProfileDetails().observe(viewLifecycleOwner, { userData: ProfileInfoModel? -> userData?.let { populateUserInfoField(it) } })
        }
    private val userCars: Unit
        get() {
            viewModel!!.getUserCars()
            viewModel!!.userCars().observe(viewLifecycleOwner, { carModels: ArrayList<CarModel>? ->
                carModels?.let {
                    val adapter = CarsAdapter(it, this)
                    userCarList!!.adapter = adapter
                }
            })
        }

    private fun populateUserInfoField(userInfo: ProfileInfoModel) {
        nameTV!!.text = userInfo.emri
        phoneTV!!.text = userInfo.phone
        ageTV!!.text = userInfo.birthday
        generTV!!.text = userInfo.gener
        user_info_img.load(userInfo.userImgRef) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
        requireContext()
    }

    private fun setupCarRecyclerView() {
        userCarList!!.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }

    override fun onCarSelected(carRef: String) {
        viewModel!!.registerCarSelected(carRef)
        viewModel!!.isCarRegisterSuccessfully.observe(viewLifecycleOwner, { isSuccess: Boolean ->
            if (isSuccess) {
                Helpers.showToastMessage(context, "Makina u ruajt me sukses")
            }
        })
    }
}