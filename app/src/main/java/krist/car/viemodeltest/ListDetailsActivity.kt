package krist.car.viemodeltest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ListDetailsActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().addToBackStack()

        supportFragmentManager.commit {

        }
    }
}