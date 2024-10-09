package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EnglishActivity : AppCompatActivity() {

    // Questions and choices array
    private val questions = arrayOf(
        "Choose the correct synonym for the word “Happy”.",
        "Which sentence is grammatically correct?",
        " Choose the correct preposition: She is going ___ school.",
        "What is the past tense of the verb go?",
        "Select the correct antonym for the word “Fast”."
    )

    private val choices = arrayOf(
        arrayOf("Sad", "Joyful", "Angry", "Tired"),
        arrayOf("She don't like ice cream.", "She doesn't likes ice cream.", "She doesn't like ice cream.", "She not like ice cream."),
        arrayOf("at", "to", "on", "in"),
        arrayOf("Goed", "Going", "Goes", "Went"),
        arrayOf("Slow", "Quick", "Rapid", "Speedy")
    )

    private val correctAnswers = arrayOf(1, 2, 1, 3, 0)  // Correct answer index

    private var currentQuestion = 0
    private lateinit var questionTextView: TextView
    private lateinit var choicesRadioGroup: RadioGroup
    private lateinit var progressBar: ProgressBar
    private lateinit var submitButton: Button
    private lateinit var previousButton: Button
    private lateinit var choiceButtons: Array<RadioButton>
    private lateinit var bckhome: ImageButton
    private lateinit var profle: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_english)

        // Initialize views
        questionTextView = findViewById(R.id.questionTextView)
        choicesRadioGroup = findViewById(R.id.choicesRadioGroup)
        progressBar = findViewById(R.id.progressBar)
        submitButton = findViewById(R.id.submitAnswerButton)
        previousButton = findViewById(R.id.button)
        bckhome = findViewById(R.id.btnhome)
        profle = findViewById(R.id.profile)

        // Find radio buttons
        choiceButtons = arrayOf(
            findViewById(R.id.choice1RadioButton),
            findViewById(R.id.choice2RadioButton),
            findViewById(R.id.choice3RadioButton),
            findViewById(R.id.choice4RadioButton)
        )

        // Set progress bar max
        progressBar.max = questions.size

        // Display first question
        displayQuestion(currentQuestion)

        // Handle submit button click
        submitButton.setOnClickListener {
            val selectedId = choicesRadioGroup.checkedRadioButtonId
            if (selectedId != -1) {
                val selectedIndex = choicesRadioGroup.indexOfChild(findViewById(selectedId))

                // Check answer
                if (selectedIndex == correctAnswers[currentQuestion]) {
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()

                    // Update progress bar
                    progressBar.progress = currentQuestion + 1

                    // Move to next question
                    currentQuestion++
                    if (currentQuestion < questions.size) {
                        displayQuestion(currentQuestion)
                    } else {
                        Toast.makeText(this, "Quiz completed!", Toast.LENGTH_SHORT).show()
                        navigateToPassScreen() // Redirect to the home screen
                    }
                } else {
                    // If answer is wrong, show a try again message
                    Toast.makeText(this, "Wrong answer! Please try again.", Toast.LENGTH_SHORT)
                        .show()
                }

            } else {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle previous button click
        previousButton.setOnClickListener {
            if (currentQuestion > 0) {
                currentQuestion--
                displayQuestion(currentQuestion)
                progressBar.progress = currentQuestion // Update progress bar
            } else {
                Toast.makeText(this, "This is the first question.", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle home button click (bckhome)
        bckhome.setOnClickListener {
            val homeIntent = Intent(this, HomeActivity::class.java) // Navigate to HomeActivity
            startActivity(homeIntent)
            finish() // Close the current activity to avoid going back
        }

        // Handle profile button click (profile)
        profle.setOnClickListener {
            val homeIntent = Intent(this, MyProfile::class.java) // Navigate to HomeActivity
            startActivity(homeIntent)
            finish() // Close the current activity to avoid going back
        }
    }

    // Function to display a question
    private fun displayQuestion(index: Int) {
        questionTextView.text = questions[index]
        for (i in choiceButtons.indices) {
            choiceButtons[i].text = choices[index][i]
        }

        // Hide or disable previous button on the first question
        if (index == 0) {
            previousButton.isEnabled = false  // Disable the button
            previousButton.alpha = 0.5f       // Change opacity to indicate it's inactive (optional)
        } else {
            previousButton.isEnabled = true   // Enable the button on subsequent questions
            previousButton.alpha = 1f         // Reset opacity to normal
        }

        // Reset the radio group selection
        choicesRadioGroup.clearCheck()
    }

    // Function to navigate to the home screen after quiz completion
    private fun navigateToPassScreen() {
        val homeIntent = Intent(
            this,
            PassActivity::class.java
        ) // Assuming your home activity is called HomeActivity
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(homeIntent)
        finish() // Close the current activity
    }
}