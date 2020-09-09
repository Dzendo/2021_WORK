package com.app4web.asdzendo.todo.ui

import android.widget.EditText
import androidx.databinding.BindingConversion
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseMethod
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


// android:text="@={Converter.dateToString(viewmodel.birthDate)}"
// @InverseBindingMethod
// android:text="@{FactDetailViewModel.fact.factId}"
//@InverseMethod("captureLongValue")
//@InverseMethod("convertLongToString")
@BindingConversion
fun convertLongToString(long: Long?): String = long?.toString() ?: ""
@InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
fun captureLongValue(view: EditText): Long {
    var value: Long = 0L
    try {
        value = view.text.toString().toLong()
    } catch (e: NumberFormatException) {
        e.printStackTrace()
    }
    return value
}

//  android:text="@{FactDetailViewModel.fact.toWork}"
@BindingConversion
fun convertBooleanToString(boolean: Boolean?): String = boolean?.toString() ?: ""
@InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
fun convertStringToBoolean(view: EditText): Boolean?  = view.text.toString().trim().toBoolean()

@BindingConversion
fun convertDateToString(date: Date?): String =
        if (date!=null)
        //    SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS", Locale.ENGLISH).format(date)?:""
        SimpleDateFormat("dd.MM.yyyy HH:mm:ss:SSS", Locale.ENGLISH).format(date)?:""
        else ""

@InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
fun captureDateValue(view: EditText): Date? {
    val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss:SSS", Locale.ENGLISH)
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
    val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
    val ccc = sdf.format(calendar.time)
   Timber.i("ToDo convertCalendarToString $ccc ")
    return ccc
}

@InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
fun captureCalendarValue(view: EditText): Calendar? {
    val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
    val value: Calendar? = Calendar.getInstance()
    try {
            value?.time = sdf.parse(view.text.toString())?: Calendar.getInstance().time
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return value
}

object BindingConverters {

    @InverseMethod(value = "convertStringToLong")
    @JvmStatic fun convertLongToString(long: Long?): String = long?.toString() ?: ""
    @JvmStatic fun convertStringToLong(text: String): Long? =
        try { text.toLong() } catch (e: NumberFormatException) { 0L }

    @InverseMethod(value = "convertStringToBoolean")
    @JvmStatic fun convertBooleanToString(boolean: Boolean?): String = boolean?.toString() ?: ""
    @JvmStatic fun convertStringToBoolean(text: String): Boolean?  = text.trim().toBoolean()

    @InverseMethod(value = "convertStringToDate")
    @JvmStatic fun convertDateToString(date: Date?): String =
            (date?:Date()).let{SimpleDateFormat("dd.MM.yyyy HH:mm:ss:SSS", Locale.ENGLISH).format(it)}
    @JvmStatic fun convertStringToDate(text: String): Date? =
            try { SimpleDateFormat("dd.MM.yyyy HH:mm:ss:SSS", Locale.ENGLISH) .parse(text) }
            catch (e: Exception) { Date()}

    @InverseMethod(value = "convertStringToCalendar")
    @JvmStatic fun convertCalendarToString(calendar: Calendar?): String =
            calendar?.let { SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).format(it.time) }?:"nul"
    @JvmStatic fun convertStringToCalendar(text: String): Calendar? = Calendar.getInstance().let {
        try {
            it.time = SimpleDateFormat("dd.MM.yyyy HH:mm:ss:SSS", Locale.ENGLISH).parse(text)?:Date()
            it
        } catch (e: Exception) { it }
    }

    @InverseMethod(value = "convertStringToInt")
    @JvmStatic fun convertIntToString(value: Int): String = value.toString()
    @JvmStatic fun convertStringToInt(text: String): Int =
               try { text.toInt() }
               catch (e: NumberFormatException) { 0 }

    @InverseMethod(value = "convertStringToChar")
    @JvmStatic fun convertCharToString(char: Char?): String = char?.toString() ?: " "
    @JvmStatic fun convertStringToChar(text: String): Char? =
               try { text.toCharArray()[0] }
               catch (e: ArrayIndexOutOfBoundsException) { ' ' }
}



// <TextView
//        android:id="@+id/number"
//        android:text='@={Converter.convertIntToString(myViewModel.number)}'
//
//        />