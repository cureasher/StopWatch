package com.jiny.stopwatch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jiny.stopwatch.databinding.ActivityStopwatchBinding
import java.util.*

class StopWatchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStopwatchBinding
    private var time = 0
    private var subtime = 0
    private var isRunning = false
    private var timerTask: Timer? = null
    private var lap = 1
    private val recordList: MutableList<TimeRecordVO> = mutableListOf()

    var recordTime = ""
    var subRecordTime = ""

    var hour = ""
    var minute = ""
    var seconds = ""
    var milliseconds = ""
    var subhour = ""
    var subminute = ""
    var subseconds = ""
    var submilliseconds = ""

    lateinit var manager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStopwatchBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        manager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, true)
        with(binding) {
            readRV()
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
                            stopWatchRV.apply {
                                val maxRecordTime =
                                    recordList.maxBy(TimeRecordVO::recordTime)!!.recordTime
                                val minRecordTime =
                                    recordList.minBy(TimeRecordVO::recordTime)!!.recordTime
                                var maxLap = recordList.maxBy { it.recordTime == maxRecordTime }.lap
                                var minLap = recordList.minBy { it.recordTime == maxRecordTime }.lap
                                readRV()
                                adapter?.notifyDataSetChanged()

                                Log.d("로그", "max: $maxRecordTime, min: :$minRecordTime")
                                Log.d("로그", "max: $maxLap, min: :$minLap")
                            }


                        }
                    }
                    "초기화" -> {
                        time = 0
                        lap = 1
                        allTimeHourTV.text = "00"
                        allTimeMinuteTV.text = ":00"
                        allTimeSecondTV.text = ".00"
                        allTimeMilliSecondTV.text = ".00"
                        recordTimeHourTV.text = "00"
                        recordTimeMinuteTV.text = ":00"
                        recordTimeSecondTV.text = ".00"
                        timerTask?.cancel()
                        recordList.clear()
                        readRV()
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
            val submillisecondTime = time % 100 // 1밀리초

            // 전체시간
            hour = timeFormat(hourTime)
            minute = timeFormat(minuteTime)
            seconds = timeFormat(secondTime)
            milliseconds = timeFormat(millisecondTime)

            // 부분시간
            subhour = timeFormat(subHourTime)
            subminute = timeFormat(subMinuteTime)
            subseconds = timeFormat(subSecondTime)
            submilliseconds = timeFormat(submillisecondTime)

            // 전역 변수 리스트에 담을
            recordTime = "$hour:$minute:$seconds.$milliseconds"
            subRecordTime = "$subhour:$subminute.$subseconds.$submilliseconds"

            // UI 변경 코드
            runOnUiThread {
                with(binding) {
                    allTimeHourTV.text = hour
                    allTimeMinuteTV.text = ":$minute"
                    allTimeSecondTV.text = ".$seconds"
                    allTimeMilliSecondTV.text = ".$milliseconds"

                    recordTimeHourTV.text = subhour
                    recordTimeMinuteTV.text = ":$subminute"
                    recordTimeSecondTV.text = ".$subseconds"
                }
            }
        }
    }

    private fun pause() {
        binding.startAndStopBT.text = "계속"
        binding.recordBT.text = "초기화"
        timerTask?.cancel()
    }

    private fun currentTapTime() {
        recordList.add(TimeRecordVO(lap++, subRecordTime, recordTime))
    }

    private fun timeFormat(time: Int) = String.format("%02d", time)

    private fun readRV() {
        with(binding.stopWatchRV) {
            layoutManager = manager
            adapter = StopWatchRecyclerViewAdapter(recordList)
            adapter?.notifyDataSetChanged()
        }
    }
}