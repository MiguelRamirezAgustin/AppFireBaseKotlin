package com.ejemplo.projectuserfirebase.Activitys


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import com.ejemplo.projectuserfirebase.LoginActivity
import com.ejemplo.projectuserfirebase.NewUserActivity
import com.ejemplo.projectuserfirebase.R
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_reusable.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ReusableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reusable)
        val df: DateFormat = SimpleDateFormat("EEEE /MM/yyyy")
        val date: String = df.format(Calendar.getInstance().time)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        val user:String = intent.getStringExtra("user").toString()
        textViewEmail.setText(" "+user)
        textViewDate.setText(" "+date)
        btnGo.setOnClickListener {
            val _intent = Intent(this, NewUserActivity::class.java)
            startActivity(_intent)
            finish()
        }
    }
}