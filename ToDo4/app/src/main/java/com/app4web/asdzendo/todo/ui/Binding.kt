package com.app4web.asdzendo.todo.ui

import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.databinding.InverseBindingAdapter
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


// android:text="@{FactDetailViewModel.fact.factId}"
@BindingConversion
fun convertLongToString(long: Long?) = long?.toString() ?: ""
@InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
fun captureLongValue(view: EditText): Long {
    var value: Long = 0
    try {
        value = view.text.toString().toLong()
    } catch (e: NumberFormatException) {
        e.printStackTrace()
    }
    return value
}

//  android:text="@{FactDetailViewModel.fact.toWork}"
@BindingConversion
fun convertBooleanToString(boolean: Boolean?) = boolean?.toString() ?: ""
@InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
fun convertStringToBoolean(view: EditText): Boolean?  = when(view.text.toString().capitalize())
    {
        "TRUE" -> true
        "FALSE" -> false
        else -> null
    }

@BindingConversion
fun convertCharToString(char: Char?) = char?.toString() ?: " "
@InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
fun convertStringToChar(view: EditText): Char? = view.text.toString()[0]

@BindingConversion
fun convertDateToString(date: Date?) =
        if (date!=null)
        SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS", Locale.ENGLISH).format(date)?:""
        else ""

@InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
fun captureDateValue(view: EditText): Date? {
    val sdf = SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS", Locale.ENGLISH)
    var value: Date? = Date()
    try {
        value = sdf.parse(view.text.toString())
    } catch (e: NumberFormatException) {
        e.printStackTrace()
    }
    return value
}
@BindingConversion
fun convertCalendarToString(calendar: Calendar?): String //= calendar.toString()
{
    if (calendar == null) return "nul"
    val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
    val ccc = sdf.format(calendar.time)
   Timber.i("ToDo convertCalendarToString $ccc ")
    return ccc
}

@InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
fun captureCalendarValue(view: EditText): Calendar? {
    val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
    val value: Calendar? = Calendar.getInstance()
    try {
            value?.time = sdf.parse(view.text.toString())?: Calendar.getInstance().time
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return value
}

//fun convertCharToString(paemi: TextView): CharSequence {
//return paemi.toString()
//}
