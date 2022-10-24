package com.naayee.naayeeclient.codes.model

data class DateTimeSelectionModel(var type: Int, var data: Any? = null,var isSelected:Boolean=false)
data class DateItem(var text: String? = null)
data class TimeItem(var text: String? = null)
data class TitleItem(var text: String? = null)