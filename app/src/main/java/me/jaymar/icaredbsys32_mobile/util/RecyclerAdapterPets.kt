package me.jaymar.icaredbsys32_mobile.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.jaymar.icaredbsys32_mobile.R
import me.jaymar.icaredbsys32_mobile.data.PetData

class RecyclerAdapterPets: RecyclerView.Adapter<RecyclerAdapterPets.ViewHolder>() {

    private val pet_information = mutableListOf<PetData>()
    private val pet_icon = mutableListOf<Int>()

    fun pushData(pet_data: PetData){
        pet_information.add(pet_data)
        pet_icon.add(R.drawable.pet_icon)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterPets.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapterPets.ViewHolder, position: Int) {

        holder.petName.text = pet_information[position].name
        holder.breed.text = pet_information[position].breed
        holder.specie.text = pet_information[position].specie
        holder.gender.text = pet_information[position].getGender()
        holder.bloodType.text = pet_information[position].blood_type
        holder.weight.text = "Weight: ${pet_information[position].weight}kg"
        if(pet_information[position].weight == 0.0)
            holder.weight.text= ""
        else holder.icon.setImageResource(pet_icon[position])

        if(pet_information[position].specie.lowercase().contains("dog"))
            holder.icon.setImageResource(R.drawable.icon_dog)
        else if(pet_information[position].specie.lowercase().contains("cat"))
        holder.icon.setImageResource(R.drawable.icon_cat)
        else if(pet_information[position].specie.lowercase().contains("rabbit"))
            holder.icon.setImageResource(R.drawable.icon_rabbit)
        else if(pet_information[position].specie.lowercase().contains("hamster"))
            holder.icon.setImageResource(R.drawable.icon_hamster)
        else if(pet_information[position].specie.lowercase().contains("bird") || pet_information[position].specie.lowercase().contains("parrot"))
            holder.icon.setImageResource(R.drawable.icon_bird)
    }

    override fun getItemCount(): Int {
        return pet_information.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var petName: TextView
        var breed: TextView
        var specie: TextView
        var gender: TextView
        var bloodType: TextView
        var weight: TextView
        var icon: ImageView

        init{
            petName = itemView.findViewById(R.id.card_view_text_name)
            breed = itemView.findViewById(R.id.card_view_text_breed)
            specie = itemView.findViewById(R.id.card_view_text_specie)
            gender = itemView.findViewById(R.id.card_view_text_gender)
            bloodType = itemView.findViewById(R.id.card_view_text_blood_type)
            weight = itemView.findViewById(R.id.card_view_text_weight)
            icon = itemView.findViewById(R.id.card_view_image)
        }

    }
}