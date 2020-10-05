package krist.car.search_trips

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import krist.car.models.TripsModel
import java.util.*

class SearchTripsViewModel(private val repo: SearchTripsRepo = SearchTripsRepo()): ViewModel() {
    private var trips = MutableLiveData<ArrayList<TripsModel>>()
    private var queryTrips = MutableLiveData<ArrayList<TripsModel>>()
    private var searchSuggestionList = MutableLiveData<List<String>>()
    private var suggestionList = arrayListOf<String>()

    fun getAllTrips(): LiveData<ArrayList<TripsModel>> = trips
    fun getSuggestionBaseOnUserInput() : LiveData<List<String>> = searchSuggestionList
    fun getQueryTrips(): LiveData<ArrayList<TripsModel>> = queryTrips

    fun getTrips() { trips = repo.getAllTrips() }

    fun getTripsForQuery(query: String) {
        val filterTrips = arrayListOf<TripsModel>()
        for(trips: TripsModel in trips.value!!) {
            if(trips.search.toLowerCase(Locale.getDefault()).contains(query.toLowerCase(Locale.getDefault()))) {
                filterTrips.add(trips)
            }
        }
        queryTrips.value = filterTrips
    }

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

    fun resetToAllTrips() {
        trips.value = trips.value
    }
}