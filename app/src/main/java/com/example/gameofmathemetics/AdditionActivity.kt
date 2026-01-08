package com.example.gameofmathemetics

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gameofmathemetics.databinding.ActivityAdditionBinding
import java.util.Locale
import kotlin.random.Random

class AdditionActivity : AppCompatActivity() {
    lateinit var additionBinding: ActivityAdditionBinding
    var correctAnswer = 0
    var userScore = 0
    var userLife = 3
    lateinit var timer : CountDownTimer
    private val startTimerInMillis : Long = 60000
    private var timeLeftInMillis : Long = startTimerInMillis


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        additionBinding = ActivityAdditionBinding.inflate(layoutInflater)
        val view = additionBinding.root
        setContentView(view)

        supportActionBar!!.title = "Addition"

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        gameContinue()

        additionBinding.ok.setOnClickListener {
            val input = additionBinding.answerText.text.toString()

            if(input == "") {
                Toast.makeText(this, "Please enter an answer", Toast.LENGTH_SHORT).show()
            } else {
                pauseTimer()
                val userAnswer = input.toInt()

                if(userAnswer == correctAnswer) {
                    userScore += 10
                    additionBinding.textScore.text = userScore.toString()
                    additionBinding.questionText.text =
                        getString(R.string.congratulations_the_answer_is_correct)
                } else {
                    userLife--
                    additionBinding.textLife.text = userLife.toString()
                    additionBinding.questionText.text =
                        getString(R.string.sorry_your_answer_is_wrong)
                    Toast.makeText(applicationContext, "You have $userLife chances remaining", Toast.LENGTH_SHORT).show()
                }

            }
        }

        additionBinding.next.setOnClickListener {
            pauseTimer()
            resetTimer()
            additionBinding.answerText.text.clear()

            if(userLife == 0) {
                Toast.makeText(applicationContext, "Game Over", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@AdditionActivity, ResultActivity::class.java)
                intent.putExtra("score", userScore)
                startActivity(intent)
                finish()

            } else {
                gameContinue()
            }
        }
    }

    fun gameContinue() {
        val firstNumber = Random.nextInt(0, 100)
        val secondNumber = Random.nextInt(0, 100)

        additionBinding.questionText.text = "$firstNumber + $secondNumber"
        correctAnswer = firstNumber + secondNumber

        startTimer()

    }

    fun startTimer() {
       timer = object : CountDownTimer(timeLeftInMillis, 1000) {

           override fun onTick(millisUntilFinished: Long) {
               timeLeftInMillis = millisUntilFinished
               updateText()

           }

           override fun onFinish() {
               pauseTimer()
               resetTimer()
               updateText()

               userLife--
               additionBinding.textLife.text = userLife.toString()
               additionBinding.questionText.text = getString(R.string.sorry_your_time_is_up)

           }

       }.start()
    }

    fun updateText() {
        val remainingTime : Int = (timeLeftInMillis/1000).toInt()
        additionBinding.textTime.text = String.format(Locale.getDefault(), "%02d", remainingTime)

    }

    fun pauseTimer() {
        timer.cancel()
    }

    fun resetTimer() {
        timeLeftInMillis = startTimerInMillis
        updateText()

    }
}