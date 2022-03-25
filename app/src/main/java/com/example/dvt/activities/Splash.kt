package com.example.dvt.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.dvt.R
import com.example.dvt.roomdatabase.RoomViewModel


@Suppress("DEPRECATION")
class Splash : AppCompatActivity() {

    private lateinit var roomViewModel: RoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        roomViewModel = RoomViewModel(this.application)
        roomViewModel.getDevControlLiveData()

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //This method is used so that your splash activity
        //can cover the entire screen.

        Handler().postDelayed({
            val i = Intent(this@Splash, MainActivity::class.java)
            //Intent is used to switch from one activity to another.
            startActivity(i)
            //invoke the SecondActivity.
            finish()
            //the current activity will get finished.
        }, 3000)
    }
}