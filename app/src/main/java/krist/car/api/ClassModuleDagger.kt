package krist.car.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ClassModuleDagger {
    @Provides
    fun provideSmth(firebaseApi: FirebaseApi) : FirebaseApiInterface = firebaseApi
}