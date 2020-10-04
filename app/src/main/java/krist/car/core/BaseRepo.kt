package krist.car.core

import krist.car.api.ApiSingleton

open class BaseRepo(val api: ApiSingleton? = ApiSingleton.getInstance()) {
    val userId: String = api!!.userUId
}