package krist.car.db

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUserCredentials(): UserCredentials

    @Insert
    fun insertUserCredentials(userCredentials: UserCredentials)
}

@Entity
data class UserCredentials(
        var email: String,
        var password: String
)