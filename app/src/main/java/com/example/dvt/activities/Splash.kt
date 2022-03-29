package com.example.dvt.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.dvt.R
import java.util.*


@Suppress("DEPRECATION")
class Splash : AppCompatActivity() {

    private lateinit var mainBackground: RelativeLayout
    var myarray = intArrayOf(R.drawable.morning, R.drawable.midday, R.drawable.night)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mainBackground = findViewById(R.id.mainBackground)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //This method is used so that your splash activity
        //can cover the entire screen.

        val r = Random()
        val i: Int = r.nextInt(2)
        this@Splash.window.setBackgroundDrawableResource(myarray[i])

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