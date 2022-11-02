package com.example.customchatbot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class login : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    val etEmailRegister = findViewById<TextView>(R.id.etEmailRegister)
    val etPasswordRegister = findViewById<TextView>(R.id.etPasswordRegister)
    val etEmailLogin = findViewById<TextView>(R.id.etEmailLogin)
    val etPasswordLogin = findViewById<TextView>(R.id.etPasswordLogin)
    val btnRegister = findViewById<Button>(R.id.btnRegister)
    val btnLogin = findViewById<Button>(R.id.btnLogin)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        btnRegister.setOnClickListener {
            registerUser()
        }
        btnLogin.setOnClickListener {
            loginUser()
        }
    }

    //Create new user
    private fun registerUser() {
        val email = etEmailRegister.text.toString()
        val password = etPasswordRegister.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.createUserWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main) {
                        checkLoggedInState()
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@login, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    //Login existing user
    private fun loginUser() {
        val email = etEmailLogin.text.toString()
        val password = etPasswordLogin.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main) {
                        checkLoggedInState()
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@login, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    //If the current user is logged in, show main activity
    private fun checkLoggedInState() {
        if (auth.currentUser != null) {
            val intent = Intent(this,MainActivity::class.java)
            //  intent.putExtra("user_name",musername)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        checkLoggedInState()
    }
}