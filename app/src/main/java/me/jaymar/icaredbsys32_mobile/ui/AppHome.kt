package me.jaymar.icaredbsys32_mobile.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import me.jaymar.icaredbsys32_mobile.R

class AppHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_home)

        /*
            var account = Database.getAccounts("Papakent","papakent")

            if(account != null){
                Toast.makeText(this,"${account.account}",Toast.LENGTH_LONG).show();
                Toast.makeText(this,"${account.loginCredentials}",Toast.LENGTH_LONG).show();
                Toast.makeText(this,"${Database.getPendingSchedules(account.loginCredentials.username)}",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"Invalid Credential",Toast.LENGTH_LONG).show();
            }
         */

        val loginButton: Button = findViewById(R.id.app_login)
        val registerButton: Button = findViewById(R.id.app_signup)

        // add on click listener on login Button
        loginButton.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        // add on click listener on register Button
        registerButton.setOnClickListener{

        }
    }
}