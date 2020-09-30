package krist.car.core

import krist.car.Api.ApiSingleton

open class BaseRepo(val api: ApiSingleton? = ApiSingleton.getInstance()) {
    val userId = api!!.userUId
}