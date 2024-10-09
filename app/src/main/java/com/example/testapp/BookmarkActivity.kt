package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BookmarkActivity : AppCompatActivity() {

    private lateinit var bookmarkListView: ListView
    private lateinit var backToQuestionsButton: Button
    private val questions = arrayOf(
        "Simplify the expression. 2x + 3x - 5.",
        "Solve the equation. 3y - 7 = 2y + 5.",
        "What is the area of a triangle with a base of 10cm and a height of 6cm?",
        "What is the next term in the sequence 3, 7, 11, 15,...?",
        "What is the value of x in the equation 5x + 3 = 23."
    )

    private val choices = arrayOf(
        arrayOf("5x - 5", "5x + 5", "x + 5", "5x"),
        arrayOf("y = -12", "y = 12", "y = 0", "y = 5"),
        arrayOf("60cm²", "30cm²", "16cm²", "20cm²"),
        arrayOf("18", "19", "20", "21"),
        arrayOf("4", "5", "6", "7")
    )

    private val correctAnswers = arrayOf(
        3,  // Correct answer index for question 1
        1,  // Correct answer index for question 2
        1,  // Correct answer index for question 3
        1,  // Correct answer index for question 4
        0   // Correct answer index for question 5
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        bookmarkListView = findViewById(R.id.bookmarkListView)
        backToQuestionsButton = findViewById(R.id.toolbar)

        // Get the list of bookmarked questions from the intent
        val bookmarkedQuestions = intent.getIntegerArrayListExtra("bookmarkedQuestions")
        if (!bookmarkedQuestions.isNullOrEmpty()) {
            // Map the bookmarked question indices to the actual questions, choices, and the correct answer
            val bookmarkedItems = bookmarkedQuestions.map { index ->
                buildQuestionWithAnswers(index)
            }

            // Set up the adapter to display the bookmarked questions in the list view
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, bookmarkedItems)
            bookmarkListView.adapter = adapter
        } else {
            // Handle the case where there are no bookmarks
            Toast.makeText(this, "No bookmarks available.", Toast.LENGTH_SHORT).show()
        }

        // Set up the back to questions button
        backToQuestionsButton.setOnClickListener {
            val intent = Intent(this, MathsActivity::class.java)  // Replace QuestionsActivity with your actual questions activity
            startActivity(intent)
            finish() // Optionally call finish() to close the current activity
        }
    }

    // Helper function to format the question and its answers, highlighting the correct one
    private fun buildQuestionWithAnswers(index: Int): String {
        val questionText = questions[index]
        val answerOptions = choices[index].mapIndexed { i, option ->
            if (i == correctAnswers[index]) {
                "$option (Correct Answer)"
            } else {
                option
            }
        }.joinToString("\n")

        return "$questionText\n\n$answerOptions"
    }
}
