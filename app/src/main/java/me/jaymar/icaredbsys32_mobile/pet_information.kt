package me.jaymar.icaredbsys32_mobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.jaymar.icaredbsys32_mobile.Database.Database
import me.jaymar.icaredbsys32_mobile.data.Inquiry
import me.jaymar.icaredbsys32_mobile.data.PetData
import me.jaymar.icaredbsys32_mobile.util.RecyclerAdapterPets

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class pet_information() : Fragment(), RecyclerAdapterPets.OnPetClickListener {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapterPets: RecyclerView.Adapter<RecyclerAdapterPets.ViewHolder>? = null
    var accountId: String = "-"
    private lateinit var pets:List<PetData>
    var controller: AppCompatActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pet_information, container, false)
        pets = Database.getPetInformation(accountId)

        layoutManager = LinearLayoutManager(view.context)

        val recyclerView = view.findViewById<RecyclerView>(R.id.petsRecyclerView)

        recyclerView.layoutManager = layoutManager

        adapterPets = RecyclerAdapterPets(this)

        for(data in pets)
            (adapterPets as RecyclerAdapterPets).pushData(data)

        // (adapter as RecyclerAdapter).pushData(PetData("",0,' ',"","","",0.0))
        recyclerView.adapter = adapterPets
        return view
    }

    override fun onPetSelected(position: Int) {
        val pendingSchedules = Database.getPendingSchedules(Database.getAccounts(accountId).loginCredentials.username)
        val petSchedules = mutableListOf<Inquiry>()
        for(x in pendingSchedules)
            if(x.pet_id == pets[position].name)
                petSchedules.add(x)

        openTransactionFragment(petSchedules)
    }

    private fun openTransactionFragment(petInquiry: List<Inquiry>){
        val transaction = controller?.supportFragmentManager?.beginTransaction()
        val fragment = Transaction(petInquiry,this, controller)
        transaction?.replace(R.id.ui_fragment,fragment)
        transaction?.commit()
    }


}