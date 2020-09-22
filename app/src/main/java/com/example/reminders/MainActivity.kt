package com.example.reminders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.reminders.R
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}