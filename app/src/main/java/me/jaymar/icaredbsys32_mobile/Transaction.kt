package me.jaymar.icaredbsys32_mobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.jaymar.icaredbsys32_mobile.data.Inquiry
import me.jaymar.icaredbsys32_mobile.util.RecyclerAdapterTransaction


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class Transaction(private val petInquiry: List<Inquiry>, private val fragment: Fragment, private var controller: AppCompatActivity?) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_transaction, container, false)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(view.context)

        val recyclerView = view.findViewById<RecyclerView>(R.id.transaction_recycler)

        recyclerView.layoutManager = layoutManager

        val adapterTransaction = RecyclerAdapterTransaction(petInquiry)

        recyclerView.adapter = adapterTransaction

        // button
        val back: Button = view.findViewById(R.id.transaction_backBtn)
        back.setOnClickListener {
            val t = controller?.supportFragmentManager?.beginTransaction()
            t?.replace(R.id.ui_fragment,fragment)
            t?.commit()
        }

        return view
    }

}