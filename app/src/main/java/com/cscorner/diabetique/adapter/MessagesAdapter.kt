package com.cscorner.diabetique.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cscorner.diabetique.R
import com.cscorner.diabetique.models.Message

class MessagesAdapter(private val currentUserId: String) : ListAdapter<Message, RecyclerView.ViewHolder>(MessageDiffCallback()) {

    companion object {
        const val VIEW_TYPE_SEND = 1
        const val VIEW_TYPE_RECEIVE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SEND) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.send_msg, parent, false)
            SendViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.receive_msg, parent, false)
            ReceiveViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        if (holder is SendViewHolder) {
            holder.bind(message)
        } else if (holder is ReceiveViewHolder) {
            holder.bind(message)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)
        return if (message.senderId == currentUserId) VIEW_TYPE_SEND else VIEW_TYPE_RECEIVE
    }

    class SendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.message_send)

        fun bind(message: Message) {
            messageTextView.text = message.text
        }
    }

    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.message_receive)

        fun bind(message: Message) {
            messageTextView.text = message.text
        }
    }

    private class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.messageId == newItem.messageId
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }
}

