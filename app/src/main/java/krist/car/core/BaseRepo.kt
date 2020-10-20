package krist.car.core

import krist.car.api.ApiModule

open class BaseRepo(val api: ApiModule? = ApiModule.getInstance()) {
    val userId: String = api!!.userUId
}