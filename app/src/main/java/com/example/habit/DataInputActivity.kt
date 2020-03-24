package com.example.habit

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_data_input.*
import kotlinx.android.synthetic.main.habit_row.view.*
import kotlin.math.abs

const val ID_KEY = "ID"

class DataInputActivity : AppCompatActivity() {

    private val priorities = arrayOf(1, 2, 3, 4, 5)
    private val habitIntent = Intent()
    private var type: Int = 0
    private var color: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_input)

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorities)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        prioritiesSpinner.adapter = spinnerAdapter

        val currentHabit = intent.getSerializableExtra(KEY_FOR_HABIT)
        if (currentHabit != null) {
            currentHabit as Habit
            enterName.setText(currentHabit.habitName.toString())
            enterDescription.setText(currentHabit.description.toString())
            prioritiesSpinner.setSelection(currentHabit.priority - 1)
            enterQuantity.setText(currentHabit.quantity.toString())
            enterPeriod.setText(currentHabit.period.toString())
            color = currentHabit.color
            if (currentHabit.type == 0)
                badType.isChecked = true
            else goodType.isChecked = true
            val id = intent.getIntExtra(ID_KEY, 0)
            Log.d(LOG_DEBUG, id.toString())
            habitIntent.putExtra(ID_KEY, id)
        }

        setColors(48, 16)

        confirmHabitButton.setOnClickListener {
            val habit = Habit(
                enterName.text.toString(),
                enterDescription.text.toString(),
                color,
                prioritiesSpinner.selectedItem as Int,
                type,
                Integer.parseInt(enterQuantity.text.toString()),
                Integer.parseInt(enterPeriod.text.toString()))
            habitIntent.putExtra(KEY_FOR_HABIT, habit)
            setResult(Activity.RESULT_OK, habitIntent)
            finish()
        }
    }

    fun onRadioButtonClicked(view: View){
        if (goodType.isChecked) {
            type = 1
            Log.d(LOG_DEBUG, "1")
        }
        else if (badType.isChecked) {
            type = 0
            Log.d(LOG_DEBUG, "0")
        }
    }

    private fun setColors(size: Int, numberOfColors: Int){
        val marginSize = size / 4
        var currentPos = marginSize + size / 2
        val diff = size + marginSize * 2

        colorPicker.setOnCheckedChangeListener { _, checkedId ->
            val checked: RadioButton = findViewById(checkedId)
            color = Integer.parseInt(checked.contentDescription.toString())
        }

        for (i in 0 until numberOfColors){
            val colorRadioButton = RadioButton(this)
            val radioButtonLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

            val marginInDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, marginSize.toFloat(), resources.displayMetrics).toInt()
            radioButtonLayoutParams.setMargins(marginInDp, 0, marginInDp, 0)
            colorRadioButton.layoutParams = radioButtonLayoutParams

            colorRadioButton.id = i

            colorRadioButton.setBackgroundResource(R.drawable.shape_for_color)
            val color = getRGBColor(currentPos)
            colorRadioButton.setBackgroundColor(color)

            colorRadioButton.setButtonDrawable(R.drawable.color_picker_selector)

            colorRadioButton.contentDescription = color.toString()

            colorPicker.addView(colorRadioButton)
            currentPos += diff
        }
    }

    private fun getRGBColor(HSVPosition: Int): Int{
        val newHSV = convert(HSVPosition, 0..1151, 0..359)
        val Hsv = FloatArray(3)
        Hsv[0] = newHSV.toFloat()
        Hsv[1] = 1f
        Hsv[2] = 1f
        val res = Color.HSVToColor(Hsv)
        return res
    }

    private fun convert(number: Int, original: IntRange, target: IntRange): Int {
        val ratio = number.toFloat() / (original.last - original.first)
        return (ratio * (target.last - target.first)).toInt()
    }
}
