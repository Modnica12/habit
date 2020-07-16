package com.example.habit.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.data.LOG_DEBUG

import com.example.habit.R
import com.example.habit.presentation.HabitApp
import com.example.habit.presentation.ViewModelFactory
import com.example.habit.presentation.viewmodels.HabitsListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


// типы сортировки(по убыванию, по возростанию)
const val SORT_FROM_BIGGEST = 0
const val SORT_FROM_SMALLEST = 1

// все виды сортировки для спинера
const val sortByDate = "дате добавления"
const val sortByPriorities = "приоритетам"

private val sortTypes = arrayOf(sortByDate, sortByPriorities)

// отображение типа сортировки в enum
private val mapForSort = mapOf(
    sortByPriorities to SortBy.PRIORITY,
    sortByDate to SortBy.DATE
    )

class BottomSheetFragment : BottomSheetDialogFragment(){

    companion object {
    }

    @ExperimentalCoroutinesApi
    private lateinit var viewModel: HabitsListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_fragment, container, false)
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sortTypes)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sortSpinner.adapter = spinnerAdapter

        (requireActivity().application as HabitApp).applicationComponent.inject(this)

        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(HabitsListViewModel::class.java)

        viewModel.setStringForFilter("")

        searchByName.addTextChangedListener { input ->
            Log.d(LOG_DEBUG, "INPUT $input")
            viewModel.setStringForFilter(input.toString())
        }

        buttonFromSmallest.setOnClickListener {
            val sortType = sortSpinner.selectedItem as String
            val enum = mapForSort[sortType]
            viewModel.setSortBy(enum!!)
            viewModel.setSortType(SORT_FROM_SMALLEST)
        }

        buttonFromBiggest.setOnClickListener {
            val sortType = sortSpinner.selectedItem as String
            val enum = mapForSort[sortType]
            viewModel.setSortBy(enum!!)
            viewModel.setSortType(SORT_FROM_BIGGEST)
        }
    }

    fun get(view: View) = 1
}

enum class SortBy {
    PRIORITY,
    DATE
}
