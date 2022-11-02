package com.example.customchatbot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.customchatbot.Constants.RECEIVE_ID
import com.example.customchatbot.Constants.SEND_ID
import kotlinx.android.synthetic.main.msg_item.view.*
import java.sql.Timestamp

class MessagingAdapter:RecyclerView.Adapter<MessagingAdapter.MessageViewHolder>() {

    var messagesList = mutableListOf<Message>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.msg_item,parent,false))
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = messagesList[position]
        val tv_message = holder.itemView.findViewById<TextView>(R.id.tv_message)
        val tv_bot_message = holder.itemView.findViewById<TextView>(R.id.tv_bot_message)
        when(currentMessage.id){
            SEND_ID -> {
                tv_message.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                tv_bot_message.visibility = View.GONE
            }
            RECEIVE_ID -> {
                tv_bot_message.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                tv_message.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }

    //Create a message 'C'RUD
    fun insertMessage(message: Message){
        this.messagesList.add(message)
        notifyItemInserted(messagesList.size)
        //notifyDataSetChanged()
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init{

            //Update a message CR'U'D
            itemView.setOnClickListener {
                val newValue: Message
                val timeStamp = Timestamp(System.currentTimeMillis())
                newValue = Message("hey", SEND_ID, timeStamp.time.toString())
                itemView.tv_message.text = newValue.message
                val set = messagesList.set(adapterPosition, newValue)
                notifyItemChanged(adapterPosition)
                /*
                val newValue: Message
                val message = itemView.et_message.text.toString()
                val timeStamp = Timestamp(System.currentTimeMillis())
                val newValue = insertMessage(Message(message, SEND_ID, timeStamp.time.toString()))
                itemView.tv_message.text = newValue.message
                val set = messagesList.set(adapterPosition, newValue)
                notifyItemChanged(adapterPosition)
                 */
            }

            //Delete a message CRU'D'
            itemView.setOnLongClickListener{
                messagesList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
                //Toast.makeText(this,"Messsage deleted", Toast.LENGTH_SHORT).show();
                true
            }
        }

    }

}