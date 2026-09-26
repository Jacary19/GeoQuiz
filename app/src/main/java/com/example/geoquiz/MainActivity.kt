package com.example.geoquiz

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.Toast
import android.widget.TextView
import android.content.Intent

class MainActivity : AppCompatActivity() {
    private val questions = listOf(
        Question("The Mississippi River forms part of Arkansas's eastern border.", true),
        Question("Arkansas shares a state border with Texas.", true),
        Question("The equator passes through more continents than the Prime Meridian.", false),
        Question(
            "Alaska is both the westernmost and easternmost U.S. state when measured by longitude.",
            true
        ),
        Question("The continent of Africa extends into all four hemispheres.", true),
        Question(
            "No part of the United States is located north of Canada's southernmost point.",
            false
        )
    )

    private var currentIndex = 0
    private var score = 0
    private var answered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt("CURRENT_INDEX")
            score = savedInstanceState.getInt("SCORE")
            answered = savedInstanceState.getBoolean("ANSWERED")
        }

        currentIndex = savedInstanceState?.getInt("CURRENT_INDEX") ?: 0
        score = savedInstanceState?.getInt("SCORE") ?: 0
        answered = savedInstanceState?.getBoolean("ANSWERED") ?: false

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val trueButton = findViewById<Button>(R.id.true_button)
        val falseButton = findViewById<Button>(R.id.false_button)
        val nextButton = findViewById<Button>(R.id.next_button)
        val questionTextView = findViewById<TextView>(R.id.question_text_view)
        questionTextView.text = questions[currentIndex].text


        trueButton.setOnClickListener {
            if (answered) return@setOnClickListener

            val correctAnswer = questions[currentIndex].answer

            if (correctAnswer == true) {
                score++
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
            }

            answered = true
        }

        falseButton.setOnClickListener {
            if (answered) return@setOnClickListener
            val correctAnswer = questions[currentIndex].answer

            if (correctAnswer == false) {
                score++
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
            }
            answered = true
        }

        nextButton.setOnClickListener {

            if (currentIndex == questions.size - 1) {
                val intent = Intent(this, ScoreActivity::class.java)
                intent.putExtra("SCORE", score)
                intent.putExtra("TOTAL", questions.size)
                startActivity(intent)
            } else {
                currentIndex++
                answered = false
                questionTextView.text = questions[currentIndex].text
            }
        }


            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("CURRENT_INDEX", currentIndex)
        outState.putInt("SCORE", score)
        outState.putBoolean("ANSWERED", answered)
    }
    }
