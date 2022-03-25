package com.example.dvt.helper_class

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class FormatterHelper {

    fun getDateTime(dateTime:String): String{
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date1 = dateFormat.parse(dateTime.substring(0, 19))
        val df = SimpleDateFormat("dd-MMM-yyyy")
        return df.format(date1)
    }

    fun getTodayDate(): Date {
        val milliSeconds = System.currentTimeMillis()
        return Date(milliSeconds)
    }


}