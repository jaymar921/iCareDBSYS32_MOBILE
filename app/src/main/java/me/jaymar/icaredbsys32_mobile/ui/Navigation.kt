package me.jaymar.icaredbsys32_mobile.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import me.jaymar.icaredbsys32_mobile.*
import me.jaymar.icaredbsys32_mobile.data.ServiceData
import me.jaymar.icaredbsys32_mobile.interfaces.Communicator
import me.jaymar.icaredbsys32_mobile.util.RecyclerAdapterPets

class Navigation : AppCompatActivity(), Communicator {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapterPets: RecyclerView.Adapter<RecyclerAdapterPets.ViewHolder>? = null
    private var accountId: String = "-";
    private val serviceFragment = Services()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        if(accountId == "-")
            accountId = intent.getStringExtra("account_id").toString()


        val petFragment = pet_information()
        petFragment.controller = this
        val contactFragment = contact_us()
        val profileFragment = profile_ui()
        petFragment.accountId = accountId
        serviceFragment.accountId = accountId
        contactFragment.accountId = accountId
        profileFragment.accountId = accountId


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

    override fun passData(data: ServiceData) {
        val bundle = Bundle()
        bundle.putString("service_code", data.service_code)

        val transaction = this.supportFragmentManager.beginTransaction()
        val serviceFragment = ServiceRequest(accountId, this)

        serviceFragment.arguments = bundle

        transaction.replace(R.id.ui_fragment, serviceFragment)
        transaction.commit()
    }
}