package me.jaymar.icaredbsys32_mobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.jaymar.icaredbsys32_mobile.R
import me.jaymar.icaredbsys32_mobile.data.ServiceData
import me.jaymar.icaredbsys32_mobile.interfaces.Communicator
import me.jaymar.icaredbsys32_mobile.util.RecyclerAdapterServices

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 */
class Services : Fragment(), RecyclerAdapterServices.OnItemClickListener {

    var accountId: String = "-";
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapterServices: RecyclerView.Adapter<RecyclerAdapterServices.ViewHolder>? = null
    private lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_services, container, false)

        communicator = activity as Communicator

        layoutManager = LinearLayoutManager(view.context)
        val recyclerView = view.findViewById<RecyclerView>(R.id.service_recycler_view)

        recyclerView.layoutManager = layoutManager

        adapterServices = RecyclerAdapterServices(this)

        recyclerView.adapter = adapterServices

        return view
    }

    override fun onItemClick(position: Int) {
        when(position){
            0 -> communicator.passData(ServiceData("VX"))
            1 -> communicator.passData(ServiceData("DM"))
            2 -> communicator.passData(ServiceData("TL"))
            3 -> communicator.passData(ServiceData("AF"))
            4 -> communicator.passData(ServiceData("VA"))
        }
    }
}