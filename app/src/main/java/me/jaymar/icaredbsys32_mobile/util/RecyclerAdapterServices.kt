package me.jaymar.icaredbsys32_mobile.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import me.jaymar.icaredbsys32_mobile.R

class RecyclerAdapterServices(private val listener: OnItemClickListener): RecyclerView.Adapter<RecyclerAdapterServices.ViewHolder>() {

    private var serviceTitle = listOf("Pet Vaccination","De-worming","Tubal Ligation","Anti Fleas","Vet Assistance")
    private var serviceDescription = listOf(
        "Protect your pet from several highly contagious disease",
        "Improve your pet's health",
        "Permanently prevent your pet from having pregnancy",
        "Protect your pet from fleas and ticks",
        "Monitor your pet without any hesitation"
    )
    private var serviceImages = listOf(
        R.drawable.service_cat,
        R.drawable.service_dog,
        R.drawable.service_hamster,
        R.drawable.service_flea,
        R.drawable.service_assistance
    )

    private var serviceColors = listOf(
        R.color.cyan,
        R.color.yellow,
        R.color.pink,
        R.color.white,
        R.color.light_gray
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapterServices.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.service_card_layout, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerAdapterServices.ViewHolder, position: Int) {
        holder.title.text = serviceTitle[position]
        holder.description.text = serviceDescription[position]
        holder.image.setImageResource(serviceImages[position])
        // holder.card.setCardBackgroundColor(serviceColors[position])
    }

    override fun getItemCount(): Int {
        return serviceTitle.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var title: TextView
        var description: TextView
        var image: ImageView
        var card: CardView

        init {
            title = itemView.findViewById(R.id.service_card_title)
            description = itemView.findViewById(R.id.service_card_description)
            image = itemView.findViewById(R.id.service_card_image)
            card = itemView.findViewById(R.id.service_card)
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if(position != RecyclerView.NO_POSITION)
                listener.onItemClick(position)
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

}