package krist.car.search_trips

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import krist.car.R
import krist.car.models.TripsModel
import java.util.*
import kotlin.collections.ArrayList

class SearchTripsViewModel(private val repo: SearchTripsRepo = SearchTripsRepo()): ViewModel() {
    private var trips = MutableLiveData<ArrayList<TripsModel>>()
    private var queryTrips = MutableLiveData<ArrayList<TripsModel>>()
    private var searchSuggestionList = MutableLiveData<List<String>>()
    private var suggestionList = arrayListOf<String>()

    fun getAllTrips(): LiveData<ArrayList<TripsModel>> = trips
    fun getSuggestionBaseOnUserInput() : LiveData<List<String>> = searchSuggestionList

    fun getTrips() { trips = repo.getAllTrips() }

    /*fun queryTrips(query: String) {
        val tripsAfterQuery = ArrayList<TripsModel>()

        trips.value?.forEach { tripsModel ->
            Log.d("lol", tripsModel.search)
            if(tripsModel.search.contains(query)) {
                Log.d("lol", "asasasa")
                tripsAfterQuery.add(tripsModel)
            }
        }
        queryTrips = MutableLiveData(tripsAfterQuery)
    }*/


    fun getCitesSuggestions(context: Context) {
        suggestionList = CitiesQuery.getCitesArrayForQuery(context) as ArrayList<String>
    }

    fun getSuggestion(query: String) {
        searchSuggestionList.value = listOf()
        val filteList = arrayListOf<String>()
        if(query.isEmpty()) {
            searchSuggestionList.value = listOf()
            return
        }
        for(searchSuggestion: String in suggestionList) {
            if(searchSuggestion.toLowerCase(Locale.getDefault()).contains(query.toLowerCase(Locale.getDefault()))) {
                filteList.add(searchSuggestion)
            }
        }
        searchSuggestionList.value = filteList
    }
}