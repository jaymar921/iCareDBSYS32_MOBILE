package me.jaymar.icaredbsys32_mobile.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.jaymar.icaredbsys32_mobile.Database.Database
import me.jaymar.icaredbsys32_mobile.R
import me.jaymar.icaredbsys32_mobile.data.PetData
import me.jaymar.icaredbsys32_mobile.util.RecyclerAdapter

class PetInformation : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_information)

        val accountId:String = intent.getStringExtra("account_id").toString()

        val pets:List<PetData> = Database.getPetInformation(accountId)

        layoutManager = LinearLayoutManager(this)

        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = layoutManager

        adapter = RecyclerAdapter()

        for(data in pets)
            (adapter as RecyclerAdapter).pushData(data)

        // adapter as RecyclerAdapter).pushData(PetData("",0,' ',"","","",0.0))
        recyclerView.adapter = adapter
    }
}