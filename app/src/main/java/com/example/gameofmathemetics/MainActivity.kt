package com.example.gameofmathemetics

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gameofmathemetics.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mainBinding.add.setOnClickListener {
            val intent = Intent(this, AdditionActivity::class.java)
            startActivity(intent)
        }

        mainBinding.subtraction.setOnClickListener {
            val intent = Intent(this, SubtractionActivity::class.java)
            startActivity(intent)
        }

        mainBinding.multiplication.setOnClickListener {
            val intent = Intent(this, MultiplicationActivity::class.java)
            startActivity(intent)
        }

    }
}