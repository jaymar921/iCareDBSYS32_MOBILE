package me.jaymar.icaredbsys32_mobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class RemovedPetUI(private val petName: String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_removed_pet_u_i, container, false)

        val name: TextView = view.findViewById(R.id.remove_pet_name)
        name.text = petName

        return view
    }

}