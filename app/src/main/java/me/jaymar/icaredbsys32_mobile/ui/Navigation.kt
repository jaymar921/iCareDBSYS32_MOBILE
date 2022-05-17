package me.jaymar.icaredbsys32_mobile.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import me.jaymar.icaredbsys32_mobile.*
import me.jaymar.icaredbsys32_mobile.Database.Database
import me.jaymar.icaredbsys32_mobile.data.PetData
import me.jaymar.icaredbsys32_mobile.util.RecyclerAdapter

class Navigation : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    var accountId: String = "-";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        if(accountId == "-")
            accountId = intent.getStringExtra("account_id").toString()


        val petFragment = pet_information()
        val serviceFragment = services()
        val contactFragment = contact_us()
        val profileFragment = profile_ui()
        petFragment.accountId = accountId


        makeCurrentFragment(petFragment)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.menu_navigation)
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.pet_information -> makeCurrentFragment(petFragment)
                R.id.services -> makeCurrentFragment(serviceFragment)
                R.id.contact_us -> makeCurrentFragment(contactFragment)
                R.id.profile_ui -> makeCurrentFragment(profileFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.ui_fragment, fragment)
            commit()
        }


    /*
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.menu_navigation)
        val navigationController = findNavController(R.id.ui_fragment)

        bottomNavigationView.setupWithNavController(navigationController)

        val pets:List<PetData> = Database.getPetInformation(accountId)

        layoutManager = LinearLayoutManager(this)

        var recyclerView = findViewById<RecyclerView>(R.id.petsRecyclerView)

        recyclerView.layoutManager = layoutManager

        adapter = RecyclerAdapter()

        for(data in pets)
            (adapter as RecyclerAdapter).pushData(data)

        // (adapter as RecyclerAdapter).pushData(PetData("",0,' ',"","","",0.0))
        recyclerView.adapter = adapter
     */
}