package com.naayee.naayeeclient.codes.utility

import android.icu.util.Calendar
import com.naayee.naayeeclient.codes.activity.ServiceBookingActivity
import com.naayee.naayeeclient.codes.model.DateItem
import com.naayee.naayeeclient.codes.model.DateTimeSelectionModel
import com.naayee.naayeeclient.codes.model.TimeItem
import com.naayee.naayeeclient.codes.model.TitleItem
import javax.inject.Inject

class DateTimeSelectionUtils @Inject constructor() {
    private val list = ArrayList<DateTimeSelectionModel>()
    private val calendar by lazy { Calendar.getInstance() }


    fun prepareData(): ArrayList<DateTimeSelectionModel> {
        list.clear()
        getDateItems()
        getTimeItems()
        return list
    }

    private fun getTimeItems() {
        list.add(DateTimeSelectionModel(ServiceBookingActivity.TYPE_TITLE, data = TitleItem("Available Slots")))
        list.add(DateTimeSelectionModel(ServiceBookingActivity.TYPE_TIME, data = TimeItem("7:00-8:00")))
        list.add(DateTimeSelectionModel(ServiceBookingActivity.TYPE_TIME, data = TimeItem("")))
        list.add(DateTimeSelectionModel(ServiceBookingActivity.TYPE_TIME, data = TimeItem("")))
        list.add(DateTimeSelectionModel(ServiceBookingActivity.TYPE_TIME, data = TimeItem("")))
        list.add(DateTimeSelectionModel(ServiceBookingActivity.TYPE_TIME, data = TimeItem("")))
        list.add(DateTimeSelectionModel(ServiceBookingActivity.TYPE_TIME, data = TimeItem("")))
    }

    private fun getDateItems() {
        list.add(DateTimeSelectionModel(ServiceBookingActivity.TYPE_TITLE, data = TitleItem("Available Date")))
        for (i in 0..threshold) {
            list.add(DateTimeSelectionModel(ServiceBookingActivity.TYPE_DATE, data = DateItem( "Available Slots")))
        }
    }

    companion object {
        var threshold = 6
    }

}
