package me.jaymar.icaredbsys32_mobile.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import me.jaymar.icaredbsys32_mobile.Database.Database
import me.jaymar.icaredbsys32_mobile.R

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email: TextView = findViewById(R.id.email_login)
        val password: TextView = findViewById(R.id.password_login)

        val button: Button = findViewById(R.id.login_btn)

        button.setOnClickListener{

            val emailString = email.text.toString()
            val passwordString = password.text.toString()

            var account = Database.getAccounts(emailString,passwordString)
            if(account != null) {
                val intent = Intent(this,Navigation::class.java)
                intent.putExtra("account_id",account.account.acc_id)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"Account not found!",Toast.LENGTH_SHORT).show()
            }
        }
    }
}