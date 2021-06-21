package krist.car.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(UserCredentials::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userCredentialsDao(): UserDao
}