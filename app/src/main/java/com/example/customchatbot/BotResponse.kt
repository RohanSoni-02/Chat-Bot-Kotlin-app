package com.example.customchatbot

import com.example.customchatbot.Constants.OPEN_GALLERY
import com.example.customchatbot.Constants.OPEN_GOOGLE
import com.example.customchatbot.Constants.OPEN_SEARCH
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object BotResponse {
    fun basicResponses(_message: String): String {

        val random = (0..2).random()
        val message =_message.toLowerCase()

        return when {
            //Open Google
            message.contains("google")-> {
                OPEN_GOOGLE
            }

            //Search on the internet
            message.contains("search")-> {
                OPEN_SEARCH
            }

            message.contains("gallery")-> {
                OPEN_GALLERY
            }

            //Flips a coin
            message.contains("flip") && message.contains("coin") -> {
                val r = (0..1).random()
                val result = if (r == 0) "heads" else "tails"

                "I flipped a coin and it landed on $result"
            }

            //Math calculations
            message.contains("solve")-> {
                val equation: String? = message.substringAfterLast("solve")
                return try {
                    val answer = SolveMath.solveMath(equation ?: "0")
                    "$answer"

                } catch (e: Exception) {
                    "Sorry, I can't solve that."
                }
            }

            message.contains("commands") -> {
                "Greetings!\n -Use the following commands: \n 'google' - To launch google \n\n'gallery' - To launch your gallery\n\n 'game'  - To play rock, paper, scissor\n\n 'joke' - To listen to a joke \n\n'time' - Tells you the current time \n'date' - Tells you the current date\n\n'solve' - Solves basic arithmetic  problems\n\n'flip a coin' - To flip a coin"
            }

            message.contains("joke") -> {
                when (random) {
                    0 -> "What is sticky and brown? A stick!"
                    1 -> "I hate Russian dolls… they're so full of themselves!"
                    2 -> "What did one plate say to his friend? Tonight, dinner's on me!"
                    else -> "error" }
            }

            message.contains("game")  -> {
                "Playing rock, paper, scissors\nI have chosen what to pull \nNow you choose as :\npull 'Your Choice'"
            }

            message.contains("pull rock") ||  message.contains("pull paper") || message.contains("pull scissors") -> {
                when (random) {
                    0 -> "I chose rock!"
                    1 -> "I chose scissors!"
                    2 -> "I chose paper!"
                    else -> "error"
                }
            }

            //How are you?
            message.contains("how are you")-> {
                when (random) {
                    0 -> "I'm doing fine, thanks!"
                    1 -> "I am feeling happy to be able to talk with you!"
                    2 -> " I am pretty good! How about you?"
                    else -> "error"
                }
            }

            message.contains("bye") || message.contains("see you")-> {
                when (random) {
                    0 -> "Bye"
                    1 -> "See you again!"
                    2 -> "Good day!"
                    else -> "error"
                }
            }

            //What time is it?
            message.contains("time")|| message.contains("date")-> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy || HH:mm")
                val date = sdf.format(Date(timeStamp.time))

                date.toString()
            }

            //Hello
            message.contains("hello") || message.contains("greetings") || message.contains("hi")  -> {
                when (random) {
                    0 -> "Namaste!"
                    1 -> "Hey there!"
                    2 -> "Bonjour!"
                    else -> "error" }
            }

            //When the programme doesn't understand...
            else -> {
                when (random) {
                    0 -> "I don't understand..."
                    1 -> "Try asking me something different"
                    2 -> "Idk"
                    else -> "error"
                }
            }
        }
    }
}