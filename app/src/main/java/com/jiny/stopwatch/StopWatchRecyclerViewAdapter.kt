package com.jiny.stopwatch

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
    }

    override fun getItemCount() = recordList.size

}