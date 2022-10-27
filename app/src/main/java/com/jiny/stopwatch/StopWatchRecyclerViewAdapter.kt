package com.jiny.stopwatch

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jiny.stopwatch.databinding.RecordItemBinding

class StopWatchRecyclerViewAdapter(private val recordList: MutableList<TimeRecordVO>) :
    RecyclerView.Adapter<StopWatchRecyclerViewAdapter.StopWatchViewHolder>() {

    inner class StopWatchViewHolder(val binding: RecordItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopWatchViewHolder {
        return StopWatchViewHolder(
            RecordItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StopWatchViewHolder, position: Int) {
        val recordList = recordList[position]
        with(holder.binding) {
            lapTV.text = recordList.lap.toString()
            recordValueTV.text = recordList.recordTime
            allTimeTV.text = recordList.allTime
        }

        if (this.recordList.size > 2) {
            val maxRecordTime = this.recordList.maxBy(TimeRecordVO::recordTime)!!.recordTime
            val minRecordTime = this.recordList.minBy(TimeRecordVO::recordTime)!!.recordTime
            when (this.recordList[position].recordTime) {
                maxRecordTime -> {
                    with(holder.binding) {
                        lapTV.setTextColor(Color.RED)
                        recordValueTV.setTextColor(Color.RED)
                        allTimeTV.setTextColor(Color.RED)
                    }
                }
                minRecordTime -> {
                    with(holder.binding) {
                        lapTV.setTextColor(Color.BLUE)
                        recordValueTV.setTextColor(Color.BLUE)
                        allTimeTV.setTextColor(Color.BLUE)
                    }
                }
            }
        }
    }

    override fun getItemCount() = recordList.size
}