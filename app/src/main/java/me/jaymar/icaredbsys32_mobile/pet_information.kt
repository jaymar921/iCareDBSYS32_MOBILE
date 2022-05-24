package me.jaymar.icaredbsys32_mobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.jaymar.icaredbsys32_mobile.Database.Database
import me.jaymar.icaredbsys32_mobile.data.PetData
import me.jaymar.icaredbsys32_mobile.util.RecyclerAdapterPets

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class pet_information() : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapterPets: RecyclerView.Adapter<RecyclerAdapterPets.ViewHolder>? = null
    var accountId: String = "-";

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pet_information, container, false)
        val pets:List<PetData> = Database.getPetInformation(accountId)

        layoutManager = LinearLayoutManager(view.context)

        var recyclerView = view.findViewById<RecyclerView>(R.id.petsRecyclerView)

        recyclerView.layoutManager = layoutManager

        adapterPets = RecyclerAdapterPets()

        for(data in pets)
            (adapterPets as RecyclerAdapterPets).pushData(data)

        // (adapter as RecyclerAdapter).pushData(PetData("",0,' ',"","","",0.0))
        recyclerView.adapter = adapterPets
        return view
    }


}