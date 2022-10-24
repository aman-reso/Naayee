package com.naayee.naayeeclient.codes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naayee.naayeeclient.R
import com.naayee.naayeeclient.codes.activity.ServiceBookingActivity.Companion.TYPE_DATE
import com.naayee.naayeeclient.codes.activity.ServiceBookingActivity.Companion.TYPE_TIME
import com.naayee.naayeeclient.codes.activity.ServiceBookingActivity.Companion.TYPE_TITLE
import com.naayee.naayeeclient.codes.model.DateItem
import com.naayee.naayeeclient.codes.model.DateTimeSelectionModel
import com.naayee.naayeeclient.codes.model.SelectedItem
import com.naayee.naayeeclient.codes.model.TimeItem
import com.naayee.naayeeclient.codes.viewholder.DateSelection
import com.naayee.naayeeclient.codes.viewholder.TimeSelection
import com.naayee.naayeeclient.codes.viewholder.TitleSection

class DateTimeSelectionAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: ArrayList<DateTimeSelectionModel> = ArrayList()
    private var selectedTimeIndex: Int = -1;
    private var selectedDateIndex: Int = -1;
    fun submitList(list: ArrayList<DateTimeSelectionModel>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_TITLE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.title_container, parent, false)
                return TitleSection(view)
            }
            TYPE_DATE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.date_item_container, parent, false)
                return DateSelection(view)
            }
            TYPE_TIME -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.time_selection_layout, parent, false)
                return TimeSelection(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.title_container, parent, false)
                return TitleSection(view)
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DateSelection -> {
                holder.bindData(list[position].isSelected, list[position].data)
                holder.rootContainer?.setOnClickListener {
                    val isSelected = list[position].isSelected
                    if (selectedDateIndex != -1) {
                        list[selectedDateIndex].isSelected = false
                        notifyItemChanged(selectedDateIndex)
                    }
                    list[position].isSelected = !isSelected
                    selectedDateIndex = if (list[position].isSelected) {
                        position
                    } else {
                        -1
                    }
                    notifyItemChanged(position)
                }
            }
            is TimeSelection -> {
                holder.bindData(list[position].isSelected, list[position].data)
                holder.rootContainer?.setOnClickListener {
                    val isSelected = list[position].isSelected
                    if (selectedTimeIndex != -1) {
                        list[selectedTimeIndex].isSelected = false
                        notifyItemChanged(selectedTimeIndex)
                    }
                    list[position].isSelected = !isSelected
                    selectedTimeIndex = if (list[position].isSelected) {
                        position
                    } else {
                        -1
                    }
                    notifyItemChanged(position)
                }
            }
            is TitleSection -> {
                holder.bindData(list[position].data)
            }
        }
    }

     fun getAvailableDateAndSlots(): Pair<String?, String?> {
        var selectedTimeSlot: String? = null
        var selectedDate: String? = null
        list.forEach {
            if (it.isSelected) {
                when (it.data) {
                    is DateItem -> {
                        selectedDate = (it.data as DateItem).text
                    }
                    is TimeItem -> {
                        selectedTimeSlot = (it.data as TimeItem).text
                    }
                }
            }
        }
        return Pair(selectedDate, selectedTimeSlot)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].type
    }
}