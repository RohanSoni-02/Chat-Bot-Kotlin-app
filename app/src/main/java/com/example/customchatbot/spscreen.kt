package com.example.customchatbot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class spscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spscreen)

        Handler().postDelayed({
            val intent = Intent(this,login::class.java)
            startActivity(intent)
            finish()
        },1000)
    }
}