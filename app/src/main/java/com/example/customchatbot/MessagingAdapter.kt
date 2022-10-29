package com.example.customchatbot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.customchatbot.Constants.RECEIVE_ID
import com.example.customchatbot.Constants.SEND_ID

class MessagingAdapter:RecyclerView.Adapter<MessagingAdapter.MessageViewHolder>() {

    var messagesList = mutableListOf<Message>()

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init{
            itemView.setOnClickListener {
                messagesList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }

    }

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

    fun insertMessage(message: Message){
        this.messagesList.add(message)
        notifyItemInserted(messagesList.size)
        //notifyDataSetChanged()
    }

}