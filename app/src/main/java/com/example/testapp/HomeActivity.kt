package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.databinding.ActivityHomeBinding
import com.example.testapp.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HomeActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityHomeBinding

    private lateinit var mathsbtn: Button
    private lateinit var sciencebtn: Button
    private lateinit var englishbtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        binding.mathsbutn.setOnClickListener{
            val intent = Intent(this@HomeActivity, MathsActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.profile.setOnClickListener {
            val intent = Intent(this@HomeActivity, MyProfile::class.java)
            startActivity(intent)
            finish()
        }

        binding.sciencebtn.setOnClickListener{
            val intent = Intent(this@HomeActivity, ScienceActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.englishbtn.setOnClickListener{
            val intent = Intent(this@HomeActivity, EnglishActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.notesbtn.setOnClickListener{
            val intent = Intent(this@HomeActivity, NotesHomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.bookmarks.setOnClickListener{
            val intent = Intent(this@HomeActivity, BookmarkActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
