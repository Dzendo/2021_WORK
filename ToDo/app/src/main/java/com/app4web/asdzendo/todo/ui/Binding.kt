package com.app4web.asdzendo.todo.ui

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseMethod
import com.app4web.asdzendo.todo.launcher.PAEMI
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*

// Пока не потребовался , т.к. впрямую указал листенер в XML:
// app:OnNavigationItemSelectedListener = "@{viewmodel::onClickBottomNavView}"

// из XML BottomNavigationView меняет(подставляет) listener на указанный (ему будет передаваться выбранный пункт меню)
// app:onNavigationItemSelected = "@{viewmodel::onClickBottomNavView}" РАБОТАЕТ
//   <!-- https://issue.life/questions/45132691 -->
/*
    @BindingAdapter("onNavigationItemSelected")
    fun setOnNavigationItemSelectedListener(view: BottomNavigationView,
                                            listener: BottomNavigationView.OnNavigationItemSelectedListener?)
            { view.setOnNavigationItemSelectedListener(listener) }
 */




// android:text="@={Converter.dateToString(viewmodel.birthDate)}"
// @InverseBindingMethod
// android:text="@{FactDetailViewModel.fact.factId}"
//@InverseMethod("captureLongValue")
//@InverseMethod("convertLongToString")
/*@BindingConversion
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

@BindingConversion
fun convertIntToString(value: Int): String = value.toString()
@InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
fun convertStringToInt(text: String): Int =
        try { text.toInt() }
        catch (e: NumberFormatException) { 0 }

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
    val sdf = SimpleDateFormat("dd.MM.yyyy")
    val ccc = sdf.format(calendar.time)
   Timber.i("ToDo convertCalendarToString $ccc ")
    return ccc
}
@InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
fun captureCalendarValue(view: EditText): Calendar? {
    val sdf = SimpleDateFormat("dd.MM.yyyy")
    val value: Calendar? = Calendar.getInstance()
    try {
            value?.time = sdf.parse(view.text.toString())?: Calendar.getInstance().time
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return value
}
*/
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

    @InverseMethod(value = "convertStrToCalendar")
    @JvmStatic fun convertCalendarToStr(calendar: Calendar?): String =
            calendar?.let { SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).format(it.time) }?:"nul"
    @JvmStatic fun convertStrToCalendar(text: String): Calendar? = Calendar.getInstance().let {
        try {
            it.time = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(text)?:it.time
            it
        } catch (e: Exception) { it }
    }

    @InverseMethod(value = "convertStringToCalendar")
    @JvmStatic fun convertCalendarToString(calendar: Calendar?): String =
            calendar?.let { SimpleDateFormat("dd.MM.yyyy HH:mm:ss:SSS", Locale.ENGLISH).format(it.time) }?:"nul"
    @JvmStatic fun convertStringToCalendar(text: String): Calendar? = Calendar.getInstance().let {
        try {
            it.time = SimpleDateFormat("dd.MM.yyyy HH:mm:ss:SSS", Locale.ENGLISH).parse(text)?:it.time
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

    @InverseMethod(value = "convertStringToPaemi")
    @JvmStatic fun convertPaemiToString(paemi: PAEMI?): String? = paemi?.name
    @JvmStatic fun convertStringToPaemi(text: String): PAEMI? =
            try { PAEMI.valueOf(text) }
            catch (e: IllegalArgumentException) {
                PAEMI.N
            }
}
