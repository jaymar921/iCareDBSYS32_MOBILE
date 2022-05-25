package me.jaymar.icaredbsys32_mobile.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.jaymar.icaredbsys32_mobile.R
import me.jaymar.icaredbsys32_mobile.data.Inquiry

class RecyclerAdapterTransaction(private val transaction: List<Inquiry>): RecyclerView.Adapter<RecyclerAdapterTransaction.ViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapterTransaction.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.transaction_card, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapterTransaction.ViewHolder, position: Int) {
        holder.service.text = transaction[position].service
        holder.schedule.text = transaction[position].schedule_date.toString()
        holder.status.text = transaction[position].status.uppercase()
        holder.venue.text = transaction[position].venue
    }

    override fun getItemCount(): Int {
        return transaction.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val service: TextView = itemView.findViewById(R.id.transaction_card_service)
        val schedule: TextView = itemView.findViewById(R.id.transaction_card_date)
        val venue: TextView = itemView.findViewById(R.id.transaction_card_venue)
        val status: TextView = itemView.findViewById(R.id.transaction_card_status)
    }
}