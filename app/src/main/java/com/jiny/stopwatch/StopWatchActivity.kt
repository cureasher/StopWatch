package com.jiny.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jiny.stopwatch.databinding.ActivityStopwatchBinding

class StopWatchActivity : AppCompatActivity() {
    private lateinit var binding : ActivityStopwatchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStopwatchBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
    }
}