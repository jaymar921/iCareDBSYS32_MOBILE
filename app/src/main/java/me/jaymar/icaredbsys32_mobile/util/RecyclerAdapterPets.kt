package me.jaymar.icaredbsys32_mobile.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import me.jaymar.icaredbsys32_mobile.Database.Database
import me.jaymar.icaredbsys32_mobile.R
import me.jaymar.icaredbsys32_mobile.RemovedPetUI
import me.jaymar.icaredbsys32_mobile.data.PetData

class RecyclerAdapterPets(private val listener:OnPetClickListener,
                          private val CONTEXT: AppCompatActivity?): RecyclerView.Adapter<RecyclerAdapterPets.ViewHolder>() {

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

        holder.removeButton.setOnClickListener{
            Database.removePet(pet_information[position].id)
            Toast.makeText(holder.itemView.context,"Removed "+pet_information[position].name+" from database",Toast.LENGTH_LONG).show()
            val t = CONTEXT?.supportFragmentManager?.beginTransaction()
            t?.replace(R.id.ui_fragment,RemovedPetUI(pet_information[position].name))
            t?.commit()
        }
    }

    override fun getItemCount(): Int {
        return pet_information.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var petName: TextView
        var breed: TextView
        var specie: TextView
        var gender: TextView
        var bloodType: TextView
        var weight: TextView
        var icon: ImageView
        var removeButton: Button

        init{
            petName = itemView.findViewById(R.id.card_view_text_name)
            breed = itemView.findViewById(R.id.card_view_text_breed)
            specie = itemView.findViewById(R.id.card_view_text_specie)
            gender = itemView.findViewById(R.id.card_view_text_gender)
            bloodType = itemView.findViewById(R.id.card_view_text_blood_type)
            weight = itemView.findViewById(R.id.card_view_text_weight)
            icon = itemView.findViewById(R.id.card_view_image)
            removeButton = itemView.findViewById(R.id.remove_pet_btn)
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?){
            val position: Int = adapterPosition
            if(position != RecyclerView.NO_POSITION)
                listener.onPetSelected(position)
        }

    }

    interface OnPetClickListener{
        fun onPetSelected(position: Int)
    }
}