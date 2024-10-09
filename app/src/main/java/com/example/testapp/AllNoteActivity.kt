package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.DataClass.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllNoteActivity : AppCompatActivity() {

    private lateinit var bckhome: ImageButton
    private lateinit var profle: ImageButton
    private lateinit var bcktoadd: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private var notesList: MutableList<Note> = mutableListOf()

    // Firebase references
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all_note)

        bckhome = findViewById(R.id.btnhome)
        profle = findViewById(R.id.profile)
        bcktoadd = findViewById(R.id.addimg)
        recyclerView = findViewById(R.id.recyclerView)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Setup RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Load notes
        loadNotes()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bckhome.setOnClickListener {
            val homeIntent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent)
            finish()
        }

        profle.setOnClickListener {
            val intent = Intent(this, MyProfile::class.java)
            startActivity(intent)
            finish()
        }

        bcktoadd.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loadNotes() {
        val userId = auth.currentUser?.uid

        if (userId != null) {
            database.child("Users").child(userId).child("Notes").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    notesList.clear() // Clear existing notes

                    for (snapshot in dataSnapshot.children) {
                        val note = snapshot.getValue(Note::class.java)
                        note?.let { notesList.add(it) }
                    }

                    noteAdapter = NoteAdapter(notesList, this@AllNoteActivity)
                    recyclerView.adapter = noteAdapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle possible errors.
                }
            })
        }
    }
}
