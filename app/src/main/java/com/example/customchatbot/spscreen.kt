package com.example.customchatbot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.airbnb.lottie.LottieAnimationView

class spscreen : AppCompatActivity() {
    lateinit var lottieanimationview: LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spscreen)
        lottieanimationview = findViewById(R.id.lottie)
        lottieanimationview.playAnimation()

        Handler().postDelayed({
            val intent = Intent(this,login::class.java)
            startActivity(intent)
            finish()
        },9000)
    }
}