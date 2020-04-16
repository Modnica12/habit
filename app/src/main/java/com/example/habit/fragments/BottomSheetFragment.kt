package com.example.habit.fragments

import android.os.Bundle
import android.transition.Fade
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.example.habit.Communicator

import com.example.habit.R
import kotlinx.android.synthetic.main.bottom_sheet_fragment.*
import kotlinx.android.synthetic.main.bottom_sheet_fragment_content.*


// типы сортировки(по убыванию, по возростанию)
const val SORT_FROM_BIGGEST = 0
const val SORT_FROM_SMALLEST = 1

// все виды сортировки для спинера
const val sortByPriorities = "приоритетам"

private val sortTypes = arrayOf(sortByPriorities)

// отображение типа сортировки в enum
private val mapForSort = mapOf(sortByPriorities to SortBy.PRIORITY)

class BottomSheetFragment : Fragment(){

    companion object {
    }

    private lateinit var viewModel: HabitsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_fragment_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinnerAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, sortTypes)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sortSpinner.adapter = spinnerAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = activity?.let { ViewModelProvider(it).get(HabitsListViewModel::class.java)}!!

        // обрабатываем нажатие на кнопку создания привычки
        addHabitButton.setOnClickListener {
            val dataInputFragment = DataInputFragment()

            // анимация поялвения фрагмента
            dataInputFragment.enterTransition = Fade()
            dataInputFragment.exitTransition = Fade()

            (activity!! as Communicator).startNewFragment(dataInputFragment)
        }

        // обрабатываем ввод в поле для поиска
        searchByName.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                // пользователь нажал кнопку поиска на клавиатуре
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // изменил данные в поле ввода
                Log.d(LOG_DEBUG, "text $newText")
                if (newText != null)
                    viewModel.setStringForFilter(newText)
                else
                    viewModel.setStringForFilter("")
                viewModel.filter()
                return true
            }

        })

        // сортировка по возростанию
        buttonFromSmallest.setOnClickListener {
            val sortType = sortSpinner.selectedItem as String
            val enum = mapForSort[sortType]
            viewModel.setSortBy(enum!!)
            viewModel.setSortType(SORT_FROM_SMALLEST)
            viewModel.sort()
        }

        // сортировка по убыванию
        buttonFromBiggest.setOnClickListener {
            val sortType = sortSpinner.selectedItem as String
            val enum = mapForSort[sortType]
            viewModel.setSortBy(enum!!)
            viewModel.setSortType(SORT_FROM_BIGGEST)
            viewModel.sort()
        }
    }
}

enum class SortBy {
    PRIORITY
}
