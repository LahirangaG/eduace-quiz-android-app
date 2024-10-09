package com.example.testapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.DataClass.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class NoteAdapter(private val notesList: MutableList<Note>, private val context: Context) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notesList[position]
        holder.noteContent.text = note.content
        holder.noteTimestamp.text = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date(note.timestamp))

        holder.btnDelete.setOnClickListener {
            deleteNote(position)
        }

        holder.btnEdit.setOnClickListener {
            val editIntent = Intent(context, EditNoteActivity::class.java)
            editIntent.putExtra("noteId", note.noteId)
            editIntent.putExtra("noteContent", note.content)
            context.startActivity(editIntent)
        }
    }

    override fun getItemCount(): Int = notesList.size

    private fun deleteNote(position: Int) {
        val note = notesList[position]
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            FirebaseDatabase.getInstance().reference.child("Users").child(userId).child("Notes").child(note.noteId)
                .removeValue().addOnSuccessListener {
                    notesList.removeAt(position)
                    notifyItemRemoved(position)
                    // Show Toast message on successful deletion
                    Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    // Show Toast message on failure
                    Toast.makeText(context, "Failed to delete note", Toast.LENGTH_SHORT).show()
                }
        }
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteContent: TextView = itemView.findViewById(R.id.note_content)
        val noteTimestamp: TextView = itemView.findViewById(R.id.note_timestamp)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }
}
