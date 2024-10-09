package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MathsActivity : AppCompatActivity() {

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

    private val correctAnswers = arrayOf(3, 1, 1, 1, 0)  // Correct answer index
    private var currentQuestion = 0
    private lateinit var questionTextView: TextView
    private lateinit var choicesRadioGroup: RadioGroup
    private lateinit var progressBar: ProgressBar
    private lateinit var submitButton: Button
    private lateinit var previousButton: Button
    private lateinit var choiceButtons: Array<RadioButton>
    private lateinit var bckhome: ImageButton
    private lateinit var profle: ImageButton
    private lateinit var bookmarkButton: ImageButton
    private lateinit var bookmarks: ImageButton

    private val bookmarkedQuestions = mutableListOf<Int>()  // Store bookmarked questions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maths)

        // Initialize views
        questionTextView = findViewById(R.id.questionTextView)
        choicesRadioGroup = findViewById(R.id.choicesRadioGroup)
        progressBar = findViewById(R.id.progressBar)
        submitButton = findViewById(R.id.submitAnswerButton)
        previousButton = findViewById(R.id.button)
        bckhome = findViewById(R.id.btnhome)
        profle = findViewById(R.id.profile)
        bookmarkButton = findViewById(R.id.bookmarkButton)
        bookmarks = findViewById(R.id.bookmarks)

        choiceButtons = arrayOf(
            findViewById(R.id.choice1RadioButton),
            findViewById(R.id.choice2RadioButton),
            findViewById(R.id.choice3RadioButton),
            findViewById(R.id.choice4RadioButton)
        )

        progressBar.max = questions.size
        displayQuestion(currentQuestion)

        submitButton.setOnClickListener {
            val selectedId = choicesRadioGroup.checkedRadioButtonId
            if (selectedId != -1) {
                val selectedIndex = choicesRadioGroup.indexOfChild(findViewById(selectedId))
                if (selectedIndex == correctAnswers[currentQuestion]) {
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
                    progressBar.progress = currentQuestion + 1
                    currentQuestion++
                    if (currentQuestion < questions.size) {
                        displayQuestion(currentQuestion)
                    } else {
                        Toast.makeText(this, "Quiz completed!", Toast.LENGTH_SHORT).show()
                        navigateToPassScreen()
                    }
                } else {
                    Toast.makeText(this, "Wrong answer! Please try again.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
            }
        }

        previousButton.setOnClickListener {
            if (currentQuestion > 0) {
                currentQuestion--
                displayQuestion(currentQuestion)
                progressBar.progress = currentQuestion
            } else {
                Toast.makeText(this, "This is the first question.", Toast.LENGTH_SHORT).show()
            }
        }

        bookmarkButton.setOnClickListener {
            if (!bookmarkedQuestions.contains(currentQuestion)) {
                bookmarkedQuestions.add(currentQuestion)
                bookmarkButton.setImageResource(R.drawable.fillstar) // Change to filled star
                Toast.makeText(this, "Question bookmarked!", Toast.LENGTH_SHORT).show()
            } else {
                bookmarkedQuestions.remove(currentQuestion)
                bookmarkButton.setImageResource(R.drawable.star) // Change to outline star
                Toast.makeText(this, "Bookmark removed.", Toast.LENGTH_SHORT).show()
            }
        }

        bookmarks.setOnClickListener {
            val bookmarkIntent = Intent(this, BookmarkActivity::class.java)
            bookmarkIntent.putIntegerArrayListExtra("bookmarkedQuestions", ArrayList(bookmarkedQuestions))
            startActivity(bookmarkIntent)
        }

        bckhome.setOnClickListener {
            val homeIntent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent)
            finish()
        }
    }

    private fun displayQuestion(index: Int) {
        questionTextView.text = questions[index]
        for (i in choiceButtons.indices) {
            choiceButtons[i].text = choices[index][i]
        }

        previousButton.isEnabled = index != 0
        previousButton.alpha = if (index == 0) 0.5f else 1f

        // Reset the radio group selection
        choicesRadioGroup.clearCheck()

        // Update the bookmark button based on whether the question is bookmarked
        if (bookmarkedQuestions.contains(currentQuestion)) {
            bookmarkButton.setImageResource(R.drawable.fillstar) // Filled star
        } else {
            bookmarkButton.setImageResource(R.drawable.star) // Outline star
        }
    }

    private fun navigateToPassScreen() {
        val homeIntent = Intent(this, PassActivity::class.java)
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(homeIntent)
        finish()
    }
}
