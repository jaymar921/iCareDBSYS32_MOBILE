package me.jaymar.icaredbsys32_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import me.jaymar.icaredbsys32_mobile.Database.Database
import me.jaymar.icaredbsys32_mobile.ui.AppHome
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(this,"Trying to connect to database...",Toast.LENGTH_SHORT).show()

        // check if the database connectivity is all set
        if(Database.Connect() != null){
            Toast.makeText(this,"Connected to database",Toast.LENGTH_LONG).show()


            // start a thread that will run later (2s after)
            Executors.newSingleThreadScheduledExecutor().schedule({

                // create new intent that will go to AppHome
                val intent = Intent(this,AppHome::class.java)
                startActivity(intent)
                finish() // finish this activity, to avoid users from getting back to this activity
            }, 2, TimeUnit.SECONDS)

        }else{
            // prompt if database connectivity was not set
            Toast.makeText(this,"Failed to connect to database...",Toast.LENGTH_LONG).show()
        }

    }
}