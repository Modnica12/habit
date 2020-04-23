package com.example.habit.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.habit.*

import kotlinx.android.synthetic.main.data_input_fragment.*

class DataInputFragment : Fragment() {

    private val priorities = arrayOf(0, 1, 2)
    private var type: Int = 0
    private var color: Int = 0
    private var uid: String = ""

    companion object {
        fun newInstance() = DataInputFragment()
    }

    private lateinit var viewModel: DataInputViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.data_input_fragment, container, false)
        viewModel = activity?.let { ViewModelProvider(it).get(DataInputViewModel::class.java)}!!
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priorities)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        prioritiesSpinner.adapter = spinnerAdapter

        val currentHabitId = arguments?.getString(KEY_FOR_HABIT)

        if (currentHabitId != null) {
            // получить из бд
            viewModel.getHabitById(currentHabitId)
                .observe(viewLifecycleOwner, Observer { habit ->
                    Log.d(LOG_DEBUG, "Habit in datainput $habit")
                    enterName.setText(habit.title.toString())
                    enterDescription.setText(habit.description.toString())
                    prioritiesSpinner.setSelection(habit.priority)
                    enterQuantity.setText(habit.count.toString())
                    enterPeriod.setText(habit.frequency.toString())
                    type = habit.type
                    color = habit.color
                    if (type == 0)
                        badType.isChecked = true
                    else goodType.isChecked = true
                    uid = habit.uid
                })
        }

        typeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val checkedType: RadioButton = requireActivity().findViewById(checkedId)
            type = Integer.parseInt(checkedType.contentDescription.toString())
            Log.d(LOG_DEBUG, type.toString())
        }

        setColors(48, 16)

        confirmHabitButton.setOnClickListener {
            packHabit()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setColors(size: Int, numberOfColors: Int){
        val marginSize = size / 4
        var currentPos = marginSize + size / 2
        val diff = size + marginSize * 2

        colorPicker.setOnCheckedChangeListener { _, checkedId ->
            val checked: RadioButton = requireActivity().findViewById(checkedId)
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
        val hsv = FloatArray(3)
        hsv[0] = newHSV.toFloat()
        hsv[1] = 1f
        hsv[2] = 1f
        return Color.HSVToColor(hsv)
    }

    private fun convert(number: Int, original: IntRange, target: IntRange): Int {
        val ratio = number.toFloat() / (original.last - original.first)
        return (ratio * (target.last - target.first)).toInt()
    }

    private fun packHabit(){
        val currentQuantity = enterQuantity.text.toString()
        val currentPeriod = enterPeriod.text.toString()
        val currentName = enterName.text.toString()
        val currentDesc = enterDescription.text.toString()
        if (allFieldsFilled(currentQuantity, currentPeriod, currentDesc, currentName)) {
            val habit = Habit(
                color,
                Integer.parseInt(currentQuantity),
                0,
                currentDesc,
                Integer.parseInt(currentPeriod),
                prioritiesSpinner.selectedItem as Int,
                currentName,
                type,
                uid
            )
            viewModel.postCurrentHabit(habit)

            requireActivity().supportFragmentManager.popBackStack()
        }
        else {
            val toast = Toast.makeText(context, "Please, fill all fields", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    private fun allFieldsFilled(currentQuantity: String, currentPeriod: String,
                                currentDesc: String, currentName: String):Boolean{
        return currentQuantity != "" && currentPeriod != ""
                && currentName != "" && currentDesc != ""
    }

}
