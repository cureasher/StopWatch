package com.jiny.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.annotation.InspectableProperty.ValueType
import com.jiny.stopwatch.databinding.ActivityStopwatchBinding
import java.util.Timer

class StopWatchActivity : AppCompatActivity() {
    private lateinit var binding : ActivityStopwatchBinding
    private var time = 0
    private var isRunning = false
    private var timerTask : Timer? = null
    private var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStopwatchBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        with(binding){
            startAndStopBT.setOnClickListener {
                isRunning = !isRunning
                if(isRunning) start() else pause()
            }

            recordBT.setOnClickListener {
                if(time != 0) currentTapTime()
            }
        }
    }

    private fun start(){
        binding.startAndStopBT.text = "중지"
        binding.recordBT.text ="구간기록"
        isRunning = true
        timerTask = kotlin.concurrent.timer(period = 10){
            time++
            val hourTime = (time / 144000) % 24 // 1시간
            val minuteTime = (time / 6000) % 60 // 1분
            val secondTime = (time / 100) % 60 // 1초
            val millisecondTime = time % 100 // 1밀리초

            // 숫자 증가할때 00포맷으로 증가하는 코드
            var hour = String.format("%02d", hourTime)
            var minute = String.format("%02d", minuteTime)
            var seconds = String.format("%02d", secondTime)
            var milliseconds = String.format("%02d", millisecondTime)

            runOnUiThread {
                with(binding){
                    allTimeHourTV.text = "$minute"
                    allTimeMinuteTV.text = ":$seconds"
                    allTimeSecondTV.text = ".$milliseconds"
                }
            }
        }
    }

    private fun pause(){
        binding.startAndStopBT.text ="계속"
        binding.recordBT.text ="초기화"
        timerTask?.cancel()
    }

    private fun currentTapTime(){
        val lapTime = time
        val lapTimeIndex = TextView(this).apply {
            textSize = 20f
            gravity = Gravity.CENTER
        }
        // 체크한 시간
        val recordTime = TextView(this).apply {
            textSize = 20f
            gravity = Gravity.CENTER
        }
        // 현재 시간
        val allTime = TextView(this).apply {
            textSize = 20f
            gravity = Gravity.CENTER
        }

        lapTimeIndex.text = """${lap++}"""
        recordTime.text = ""
        allTime.text = """${String.format("%02d",(lapTime / 6000) % 60)}:${String.format("%02d",(lapTime / 100) % 60)}"""

        binding.lapLayout.addView(lapTimeIndex,0)
        binding.recordValueLayout.addView(recordTime,0)
        binding.allTimeLayout.addView(allTime,0)
    }
}