package com.jiny.stopwatch

data class TimeRecordVO(
    val lap: Int = -1,
    val recordTime: String = "00:00:00.00",
    val allTime: String = "00:00:00.00"
)
