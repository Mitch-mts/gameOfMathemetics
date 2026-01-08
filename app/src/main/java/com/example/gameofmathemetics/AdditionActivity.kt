package com.example.gameofmathemetics

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gameofmathemetics.databinding.ActivityAdditionBinding
import kotlin.random.Random

class AdditionActivity : AppCompatActivity() {
    lateinit var additionBinding: ActivityAdditionBinding
    var correctAnswer = 0
    var userScore = 0
    var userLife = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        additionBinding = ActivityAdditionBinding.inflate(layoutInflater)
        val view = additionBinding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        gameLogic()

        additionBinding.ok.setOnClickListener {
            val input = additionBinding.answerText.text.toString()
            if(input == "") {
                Toast.makeText(this, "Please enter an answer", Toast.LENGTH_SHORT).show()
            } else {
                val userAnswer = input.toInt()

                if(userAnswer == correctAnswer) {
                    userScore += 10
                    additionBinding.textScore.text = userScore.toString()
                    additionBinding.questionText.text = "Congratulations, the answer is correct"
                } else {
                    userLife--
                    additionBinding.textLife.text = userLife.toString()
                    additionBinding.questionText.text = "Sorry, your answer is wrong"
                }

            }
        }

        additionBinding.next.setOnClickListener {
            gameLogic()
            additionBinding.answerText.text.clear()
        }
    }

    fun gameLogic() {
        val firstNumber = Random.nextInt(0, 100)
        val secondNumber = Random.nextInt(0, 100)

        additionBinding.questionText.text = "$firstNumber + $secondNumber"
        correctAnswer = firstNumber + secondNumber

    }
}