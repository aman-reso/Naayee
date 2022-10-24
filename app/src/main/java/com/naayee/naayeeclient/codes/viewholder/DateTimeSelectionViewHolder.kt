package com.naayee.naayeeclient.codes.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naayee.naayeeclient.R
import com.naayee.naayeeclient.codes.model.DateItem
import com.naayee.naayeeclient.codes.model.TimeItem
import com.naayee.naayeeclient.codes.model.TitleItem


class DateSelection(var root: View) : RecyclerView.ViewHolder(root) {
    private val selectedBg by lazy { R.drawable.highlight_selected }
    private val unSelectedBg by lazy { R.drawable.border_8dp }

    private val dayTextView: TextView? by lazy { itemView.findViewById(R.id.dayTextView) }
    private val dateTextView: TextView? by lazy { itemView.findViewById(R.id.dateTextView) }
    val rootContainer: View? by lazy { itemView.findViewById(R.id.rootContainer) }

    fun bindData(isSelected: Boolean, data: Any?) {
        if (isSelected) {
            itemView.setBackgroundResource(selectedBg)
        } else {
            itemView.setBackgroundResource(unSelectedBg)
        }
        when (data) {
            is DateItem -> {
                dayTextView?.text = data.text
            }
        }
    }
}
class TimeSelection(var root: View) : RecyclerView.ViewHolder(root) {
    private val selectedBg by lazy { R.drawable.highlight_selected }
    private val unSelectedBg by lazy { R.drawable.border_8dp }

    private val timeSlotTextView: TextView? by lazy { itemView.findViewById(R.id.bookingSlotTextView) }
    val rootContainer: View? by lazy { itemView.findViewById(R.id.rootContainer) }
    fun bindData(isSelected: Boolean, data: Any?) {
        if (isSelected) {
            itemView.setBackgroundResource(selectedBg)
        } else {
            itemView.setBackgroundResource(unSelectedBg)
        }
        when (data) {
            is TimeItem -> {
                timeSlotTextView?.text = data.text
            }
        }
    }
}

class TitleSection(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleTextView: TextView? by lazy { itemView.findViewById(R.id.monthTitleTextView) }

    fun bindData(data: Any?) {
        when (data) {
            is TitleItem -> {
                titleTextView?.text = data.text
            }
        }
    }
}