package com.example.dvt.helper_class

import android.content.Context
import com.example.dvt.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class FormatterHelper {

    fun getDate(dateTime:String): String{
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date1 = dateFormat.parse(dateTime.substring(0, 18))
        val df = SimpleDateFormat("yyyy-MM-dd")
        return df.format(date1)
    }

    fun getTime(dateTime:String): String{
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date1 = dateFormat.parse(dateTime.substring(0, 18))
        val df = SimpleDateFormat("HH:mm:ss")
        return df.format(date1)
    }

    fun getTodayDateTime(): String {
        val milliSeconds = System.currentTimeMillis()
        val todayDate = getDateFromMilli(milliSeconds)
        return getDateTime(todayDate)
    }

    fun getDateFromMilli(milliseconds: Long): Date {
        return Date(milliseconds)
    }

    fun getDateTime(date: Date):String{
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return dateFormat.format(date)
    }

    fun saveSharedPreference(
        context: Context,
        sharedKey: String,
        sharedValue: String){

        val app_name = context.getString(R.string.app_name)
        val sharedPreferences = context.getSharedPreferences(app_name, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(sharedKey, sharedValue)
        editor.apply()
    }

    fun retrieveSharedPreference(
        context: Context,
        sharedKey: String): String? {

        val app_name = context.getString(R.string.app_name)

        val sharedPreferences = context.getSharedPreferences(app_name, Context.MODE_PRIVATE)
        return sharedPreferences.getString(sharedKey, null)

    }





}