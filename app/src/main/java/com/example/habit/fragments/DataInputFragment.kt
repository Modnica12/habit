package com.example.habit.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.fragment.app.FragmentManager
import com.example.habit.*

import kotlinx.android.synthetic.main.data_input_fragment.*
import kotlinx.android.synthetic.main.habit_row.view.*

class DataInputFragment : Fragment() {

    private val priorities = arrayOf(1, 2, 3, 4, 5)
    private var type: Int = 0
    private var color: Int = 0
    private var position: Int = -1

    companion object {
        fun newInstance() = DataInputFragment()
    }

    private lateinit var viewModel: DataInputViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.data_input_fragment, container, false)



        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinnerAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, priorities)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        prioritiesSpinner.adapter = spinnerAdapter


        val currentHabit = arguments?.getSerializable(KEY_FOR_HABIT)
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
            position = arguments!!.getInt(ID_KEY, -1)
            Log.d(LOG_DEBUG, id.toString())
        }

        val communicator = activity as Communicator

        //communicator.communicate(HabitsListFragment as Fragment)

        setColors(48, 16)

        typeRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val checkedType: RadioButton = activity!!.findViewById(checkedId)
            type = Integer.parseInt(checkedType.contentDescription.toString())
        }

        confirmHabitButton.setOnClickListener {
            val habit = Habit(
                enterName.text.toString(),
                enterDescription.text.toString(),
                color,
                prioritiesSpinner.selectedItem as Int,
                type,
                Integer.parseInt(enterQuantity.text.toString()),
                Integer.parseInt(enterPeriod.text.toString()))
            communicator.passData2(habit, position)
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DataInputViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun setColors(size: Int, numberOfColors: Int){
        val marginSize = size / 4
        var currentPos = marginSize + size / 2
        val diff = size + marginSize * 2

        colorPicker.setOnCheckedChangeListener { _, checkedId ->
            val checked: RadioButton = activity!!.findViewById(checkedId)
            color = Integer.parseInt(checked.contentDescription.toString())
        }

        for (i in 0 until numberOfColors){
            val colorRadioButton = RadioButton(activity)
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
