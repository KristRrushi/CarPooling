package krist.car.search_trips

import android.content.Context
import krist.car.R
import java.util.*
import kotlin.collections.ArrayList

open class CitiesQuery {
    companion object {
        fun getCitesArrayForQuery(context: Context): List<String> {
            val cites = context.resources.getStringArray(R.array.qytetet_shqiperis)
            val citestAfterModicvation = ArrayList<String>()

            for((i, v) in cites.withIndex()) {
                for((i2, v2) in cites.withIndex()) {
                    if(!v!!.contentEquals(v2)) {
                        citestAfterModicvation.add("$v $v2")
                    }
                }
            }
            return citestAfterModicvation
        }
    }
}