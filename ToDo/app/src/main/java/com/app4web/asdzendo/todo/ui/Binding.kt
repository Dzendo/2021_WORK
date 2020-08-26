package com.app4web.asdzendo.todo.ui

import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.databinding.InverseBindingAdapter

// android:text="@{BindingKt.longToStrext(FactDetailViewModel.fact.parent)}"

/*@BindingAdapter("sleepDurationFormatted")*/
//fun TextView.longToStrext(value: Long?): String {
fun longToStrext(value: Long?): String {
    return value?.toString() ?: ""
}


// android:text="@{FactDetailViewModel.fact.factId}"
@BindingConversion
fun convertLongToString(long: Long?) = long?.toString() ?: ""

//  android:text="@{FactDetailViewModel.fact.toWork}"
@BindingConversion
fun convertBooleanToString(boolean: Boolean?) = boolean?.toString() ?: ""

//  android:text="@={FactDetailViewModel.fact.paemi}"
@BindingConversion
fun convertCharToString(char: Char?) = char?.toString() ?: ""

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


class MyEditTextBindingAdapters {
//  android:text="@={FactDetailViewModel.fact.nameShort}"
//  android:text="@{Long.toString(FactDetailViewModel.fact.parent)}"
//   android:text="@{Integer.toString(FactDetailViewModel.fact.money)}"
//  android:text="@{MyEditTextBindingAdapters.INSTANCE.longToStr(FactDetailViewModel.fact.parent)}" без аннотаций


   // @BindingConversion
    fun longToStr(value: Long?): String {
        return value?.toString() ?: ""
    }
// Он работает отлично, но вам нужно установить android:inputType="number"для вашего EditText.
   /* @InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")*/
    fun captureLongValue(view: EditText): Long {
        var value: Long = 0
        try {
            value = view.text.toString().toLong()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        return value
    }
}