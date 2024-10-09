package com.example.testapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditNoteActivity : AppCompatActivity() {

    private lateinit var editNoteContent: EditText
    private lateinit var btnSaveNote: Button
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private var noteId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        editNoteContent = findViewById(R.id.editNoteContent)
        btnSaveNote = findViewById(R.id.btnSaveNote)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Get note details passed from the adapter
        noteId = intent.getStringExtra("noteId")
        val noteContent = intent.getStringExtra("noteContent")

        // Set the existing content in the EditText
        editNoteContent.setText(noteContent)

        btnSaveNote.setOnClickListener {
            saveEditedNote()
        }
    }

    private fun saveEditedNote() {
        val newContent = editNoteContent.text.toString()
        val userId = auth.currentUser?.uid

        if (userId != null && noteId != null) {
            val noteRef = database.child("Users").child(userId).child("Notes").child(noteId!!)
            noteRef.child("content").setValue(newContent).addOnSuccessListener {
                Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
                finish() // Close the activity after saving
            }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to update note", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
