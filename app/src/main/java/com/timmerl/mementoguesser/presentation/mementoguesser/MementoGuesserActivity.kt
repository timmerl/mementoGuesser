package com.timmerl.mementoguesser.presentation.mementoguesser

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.presentation.mementomanagement.MementoManagementActivity

class MementoGuesserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memento_guesser)
        findViewById<FloatingActionButton>(R.id.manageButton).setOnClickListener {
            startActivity(Intent(this, MementoManagementActivity::class.java))
        }
    }

}