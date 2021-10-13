package com.ejemplo.projectuserfirebase

import android.content.Context
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView

class SplashActivity : AppCompatActivity() {
    private lateinit var handler:Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        val backgroundImage: ImageView = findViewById(R.id.logo_icon)
        val sideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_slide)
        backgroundImage.startAnimation(sideAnimation)


        Handler().postDelayed({
            //validar si existe sesion
            val sharedPreferences= getSharedPreferences("my_aplication", Context.MODE_PRIVATE)
            val usrId = sharedPreferences.getString("usr_id_splash", "")

            if (!usrId.equals("")){
                // si existe sesion
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                //No existe sesion
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 2000) // delaying for 4 seconds...

    }


}