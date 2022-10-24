package com.jiny.stopwatch

import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jiny.stopwatch.databinding.ActivityStopwatchBinding
import java.util.*

class StopWatchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStopwatchBinding
    private var time = 0
    private var subtime = 0
    private var isRunning = false
    private var timerTask: Timer? = null
    private var lap = 1
    private val recordList: ArrayList<String> = arrayListOf()

    var hour = ""
    var minute = ""
    var seconds = ""
    var milliseconds = ""
    var subhour = ""
    var subminute = ""
    var subseconds = ""
    var submilliseconds = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStopwatchBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        with(binding) {
            startAndStopBT.setOnClickListener {
                isRunning = !isRunning
                if (isRunning) start() else pause()
            }

            recordBT.setOnClickListener {
                when (recordBT.text) {
                    "구간기록" -> {
                        if (time != 0) {
                            currentTapTime()
                            subtime = 0
                        }
                    }
                    "초기화" -> {
                        time = 0
                        recordValueLayout.removeAllViews()
                        lapLayout.removeAllViews()
                        allTimeLayout.removeAllViews()

                        allTimeHourTV.text = "00"
                        allTimeMinuteTV.text = ":00"
                        allTimeSecondTV.text = ":00"
                        recordTimeHourTV.text = "00"
                        recordTimeMinuteTV.text = ":00"
                        recordTimeSecondTV.text = ":00"
                    }

                }
            }
        }
    }

    private fun start() {
        binding.startAndStopBT.text = "중지"
        binding.recordBT.text = "구간기록"
        isRunning = true
        timerTask = kotlin.concurrent.timer(period = 10) {
            time++
            subtime++
            val hourTime = (time / 144000) % 24 // 1시간
            val minuteTime = (time / 6000) % 60 // 1분
            val secondTime = (time / 100) % 60 // 1초
            val millisecondTime = time % 100 // 1밀리초

            val subHourTime = (subtime / 144000) % 24 // 1시간
            val subMinuteTime = (subtime / 6000) % 60 // 1분
            val subSecondTime = (subtime / 100) % 60 // 1초

            // 전체시간
            hour = String.format("%02d", hourTime)
            minute = String.format("%02d", minuteTime)
            seconds = String.format("%02d", secondTime)
            milliseconds = String.format("%02d", millisecondTime)

            // 부분시간
            subhour = String.format("%02d", subHourTime)
            subminute = String.format("%02d", subMinuteTime)
            subseconds = String.format("%02d", subSecondTime)

            recordList.add("$subhour:$subminute:$subseconds")


            runOnUiThread {
                with(binding) {
                    allTimeHourTV.text = minute
                    allTimeMinuteTV.text = ":$seconds"
                    allTimeSecondTV.text = ":$milliseconds"

                    recordTimeHourTV.text = subhour
                    recordTimeMinuteTV.text = ":$subminute"
                    recordTimeSecondTV.text = ":$subseconds"
                }
            }
//            recordList.add("$minute:$seconds:$milliseconds")
        }
    }

    private fun pause() {
        binding.startAndStopBT.text = "계속"
        binding.recordBT.text = "초기화"
        timerTask?.cancel()
    }

    private fun currentTapTime() {
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
        recordTime.text = recordList.last()
        allTime.text = """$hour:$minute:$seconds"""

        binding.lapLayout.addView(lapTimeIndex, 0)
        binding.recordValueLayout.addView(recordTime, 0)
        binding.allTimeLayout.addView(allTime, 0)
    }
}