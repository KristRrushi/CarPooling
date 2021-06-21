package krist.car.viemodeltest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListDetailsMainVIewModel: ViewModel() {
    val selected = MutableLiveData<Item>()

    fun select(item: Item) {
        selected.value = item
    }

}

data class Item(val index: Int, val text: String)