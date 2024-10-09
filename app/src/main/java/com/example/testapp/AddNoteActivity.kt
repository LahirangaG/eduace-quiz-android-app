package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.DataClass.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddNoteActivity : AppCompatActivity() {

    private lateinit var bckhome: ImageButton
    private lateinit var profle: ImageButton

    // Declare Firebase references
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        bckhome = findViewById(R.id.btnhome)
        profle = findViewById(R.id.profile)

        // Initialize Firebase Auth and Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Get references to the views
        val addNoteField = findViewById<EditText>(R.id.addnotefield)
        val addNoteButton = findViewById<Button>(R.id.addnotebtn)

        // Set up the add note button click listener
        addNoteButton.setOnClickListener {
            val noteText = addNoteField.text.toString().trim()

            if (noteText.isNotEmpty()) {
                // Get the current user ID
                val userId = auth.currentUser?.uid

                // Create a unique ID for the note
                val noteId = database.child("Users").child(userId!!).child("Notes").push().key ?: return@setOnClickListener

                // Create a Note object
                val note = Note(
                    noteId = noteId,
                    content = noteText,
                    timestamp = System.currentTimeMillis()
                )

                // Save the note in the Firebase database under the user's Notes node
                database.child("Users").child(userId).child("Notes").child(noteId).setValue(note)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Note added successfully!", Toast.LENGTH_SHORT).show()

                        // Navigate to AllNotesActivity
                        val intent = Intent(this, AllNoteActivity::class.java)
                        startActivity(intent)
                        finish() // Close the current activity to avoid going back
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to add note. Try again.", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Please type a note before adding.", Toast.LENGTH_SHORT).show()
            }
        }

        bckhome.setOnClickListener {
            val homeIntent = Intent(this, HomeActivity::class.java) // Navigate to HomeActivity
            startActivity(homeIntent)
            finish() // Close the current activity to avoid going back
        }

        profle.setOnClickListener {
            val intent = Intent(this, MyProfile::class.java) // Navigate to MyProfile
            startActivity(intent)
            finish() // Close the current activity to avoid going back
        }
    }
}
