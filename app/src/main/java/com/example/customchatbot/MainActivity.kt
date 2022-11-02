package com.example.customchatbot

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.customchatbot.Constants.OPEN_GALLERY
import com.example.customchatbot.Constants.OPEN_GOOGLE
import com.example.customchatbot.Constants.OPEN_SEARCH
import com.example.customchatbot.Constants.RECEIVE_ID
import com.example.customchatbot.Constants.SEND_ID
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    val rv_messages = findViewById<RecyclerView>(R.id.rv_messages)
    val et_message = findViewById<TextView>(R.id.et_message)
    val btn_send = findViewById<Button>(R.id.btn_send)
    private lateinit var adapter: MessagingAdapter
    private val botList = listOf("RoSoBot","RohBot","BotSoni")
    //lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView()
        showFragment()
        clickEvents()

        val random = (0..2).random()
        customBotMessage("Welcome!\nYou are in conversation with ${botList[random]}, how may I help?\n\n Hint : You can type in 'commands' for the list of functionalities I can assist you with")
    }

    private fun clickEvents() {

        //Send a message
        btn_send.setOnClickListener {
            sendMessage()
        }

        //Scroll back to correct position when user clicks on text view
        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(100)

                withContext(Dispatchers.Main) {
                    rv_messages.scrollToPosition(adapter.itemCount - 1)

                }
            }
        }
    }

    private fun recyclerView() {
        adapter = MessagingAdapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(applicationContext)
    }

    override fun onStart() {
        super.onStart()
        //In case there are messages, scroll to bottom when re-opening app
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun sendMessage() {
        val message = et_message.text.toString()
        val timeStamp = Time.timeStamp()

        if (message.isNotEmpty()) {

            et_message.setText("")

            adapter.insertMessage(Message(message, SEND_ID, timeStamp))
            rv_messages.scrollToPosition(adapter.itemCount - 1)

            botResponse(message)
        }
    }

    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            //Fake response delay
            delay(1000)

            withContext(Dispatchers.Main) {
                //Gets the response
                val response = BotResponse.basicResponses(message)

                //Inserts our message into the adapter
                adapter.insertMessage(Message(response, RECEIVE_ID, timeStamp))

                //Scrolls us to the position of the latest message
                rv_messages.scrollToPosition(adapter.itemCount - 1)

                when (response) {
                    //Launch Google
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    //Search anything on the web
                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }
                    //Launch Gallery
                    OPEN_GALLERY -> {
                        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        intent.type = "image/* video/*"
                        startActivity(intent)
                    }

                }
            }
        }
    }

    //Bot reply message with receive_id
    private fun customBotMessage(message: String) {

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()
                adapter.insertMessage(Message(message, RECEIVE_ID, timeStamp))
                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    //Save messages
    /*
   fun savedData(item:Message){
       sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)?: return
       with(sharedPref.edit()){
           putString("MESSAGE", item.message)
       }
   }
    */

    //Logout user
    fun logoutfn(view: android.view.View) {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, login::class.java)
        startActivity(intent)
        finish()
    }

    //Logout button
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menuitems, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                logoutfn(this.scrollView)
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    //Modal bottom sheet fragment presented at the start of main activity
    private fun showFragment() {
        val bottomSheetFragment = ModalBottomSheetFragment()
        bottomSheetFragment.show(supportFragmentManager,bottomSheetFragment.tag)
    }
}