package me.jaymar.icaredbsys32_mobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import me.jaymar.icaredbsys32_mobile.Database.Database
import me.jaymar.icaredbsys32_mobile.util.Utility
import java.sql.Date

/**
 * A simple [Fragment] subclass.
 */
class profile_ui : Fragment() {

    var accountId: String = "-"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile_ui, container, false)

        // get account data from database
        val userDATA = Database.getAccounts(accountId)

        // set username
        val userName: TextView = view.findViewById(R.id.profile_username_txtView)
        userName.text = userDATA.loginCredentials.username

        // set full name
        val fullName: TextView = view.findViewById(R.id.profile_full_name)
        fullName.text = userDATA.account.getFullName()

        // set birthdate
        val birthdate: TextView = view.findViewById(R.id.profile_birthdate)
        birthdate.text = userDATA.account.birthdate.toString()

        // set addressLine
        val addressLine: TextView = view.findViewById(R.id.profile_address)
        addressLine.text = userDATA.account.getAddressLine()


        // update button
        val updateButton: Button = view.findViewById(R.id.profile_update_button)
        updateButton.setOnClickListener {
            val name = Utility.getFirstLastname(fullName.text.toString())
            // parse data
            userDATA.account.firstname = name[0]
            userDATA.account.lastname = name[1]
            userDATA.account.parseAddressFragment(addressLine.text.toString())
            userDATA.account.birthdate = Date.valueOf(birthdate.text.toString())

            if(Database.updateAccount(userDATA)){
                Toast.makeText(view.context,"Updated Account", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(view.context,"Failed to update account", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }


}