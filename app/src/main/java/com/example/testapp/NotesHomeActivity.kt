package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.testapp.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class NotesHomeActivity : AppCompatActivity() {

    private lateinit var bckhome: ImageButton
    private lateinit var profle: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notes_home)

        bckhome = findViewById(R.id.btnhome)
        profle = findViewById(R.id.profile)

        // Find the plus button by its ID
        val addButton = findViewById<Button>(R.id.imageView3)

        // Set up the click listener
        addButton.setOnClickListener {
            // Navigate to AddNoteActivity
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

        bckhome.setOnClickListener {
            val homeIntent = Intent(this, HomeActivity::class.java) // Navigate to HomeActivity
            startActivity(homeIntent)
            finish() // Close the current activity to avoid going back
        }

        // Find the view button by its ID
        val viewbn = findViewById<Button>(R.id.viewntebtn)

        viewbn.setOnClickListener {
            val intent = Intent(this, AllNoteActivity::class.java)
            startActivity(intent)
            finish()
        }

        profle.setOnClickListener {
            val intent = Intent(this, MyProfile::class.java) // Navigate to HomeActivity
            startActivity(intent)
            finish() // Close the current activity to avoid going back
        }
    }
}