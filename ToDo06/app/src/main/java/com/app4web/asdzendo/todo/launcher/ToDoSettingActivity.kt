package com.app4web.asdzendo.todo.launcher

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app4web.asdzendo.todo.R
import com.app4web.asdzendo.todo.ui.BindingConverters.convertDateToString
import com.app4web.asdzendo.todo.ui.BindingConverters.convertStringToDate
import kotlinx.android.synthetic.main.activity_to_do_setting.*
import java.util.*


class ToDoSettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_setting)

        database.isChecked = BASE_IN_MEMORY
        database.setOnCheckedChangeListener { buttonView, isChecked ->
            BASE_IN_MEMORY = isChecked
        }

        constfact.setText(COUNTSFact.toString())
        constfact.setOnClickListener {
            try {COUNTSFact = constfact.text.toString().toLong()} catch(e:Error) { ;}
            Toast.makeText(this, "$COUNTSFact", Toast.LENGTH_LONG).show()
        }

       // editTextDateStart.setText(convertDateToString(FilterDateStart))
       // editTextDateEnd.setText(convertDateToString(FilterDateEnd))
    }

    fun onClickOk(view: View) {
        Toast.makeText(this, "База в памяти - $BASE_IN_MEMORY  Пачка = $COUNTSFact", Toast.LENGTH_LONG).show()
       // FilterDateStart= convertStringToDate(editTextDateStart.text.toString()) ?: Date()
       // FilterDateEnd= convertStringToDate(editTextDateEnd.text.toString()) ?: Date()
        Toast.makeText(this, " ${FilterDateStart.toString()} \n ${FilterDateEnd.toString()}", Toast.LENGTH_LONG).show()
    }


    fun onClickRun(view: View) {
        val intent = Intent(this@ToDoSettingActivity, ToDoActivity::class.java)
        startActivity(intent)
    }

}