package com.cscorner.diabetique

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageButton: ImageButton = findViewById(R.id.ImageButton)

        imageButton.setOnClickListener {
            val animation = ObjectAnimator.ofFloat(imageButton, "translationY", 100f)
            animation.duration = 1000
            animation.start()
            val intent = Intent(this@MainActivity, EspacesActivity::class.java)
            startActivity(intent)
        }
    }
}