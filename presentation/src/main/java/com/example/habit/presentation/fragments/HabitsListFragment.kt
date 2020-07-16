package com.example.habit.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habit.*
import com.example.data.KEY_FOR_SAVING_TYPE_FILTER
import com.example.data.LOG_DEBUG
import com.example.data.PASS_TYPE
import com.example.habit.adapters.HabitAdapter
import com.example.habit.presentation.HabitApp
import com.example.habit.presentation.ViewModelFactory
import com.example.habit.presentation.viewmodels.HabitsListViewModel
import kotlinx.android.synthetic.main.bottom_sheet_fragment.*

import kotlinx.android.synthetic.main.habits_list_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


class HabitsListFragment : Fragment() {

    private var typeFilterValue = 1

    private lateinit var habitAdapter: HabitAdapter

    @ExperimentalCoroutinesApi
    private lateinit var viewModel: HabitsListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    companion object{
        fun newInstance(type: Int): HabitsListFragment{
            val bundle = Bundle()
            bundle.putInt(PASS_TYPE, type)
            val fragment = HabitsListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        (requireActivity().application as HabitApp).applicationComponent.inject(this)

        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(HabitsListViewModel::class.java)


        val args = arguments
        if (args != null) {
            typeFilterValue = args.getInt(PASS_TYPE, 0)
        }

        return inflater.inflate(R.layout.habits_list_fragment, container, false)
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(LOG_DEBUG, "onActivityCreated")

        Log.d(LOG_DEBUG, typeFilterValue.toString())

        habitAdapter = HabitAdapter(typeFilterValue, viewModel)

        listOfHabits.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = habitAdapter

            //разделитель для элементов списка
            val divider = ContextCompat.getDrawable(
                requireActivity().applicationContext,
                R.drawable.habits_list_divider
            )
            val dividerItemDecoration =
                DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
            dividerItemDecoration.setDrawable(divider!!)
            listOfHabits.addItemDecoration(dividerItemDecoration)
        }

        viewModel.getAllHabits().observe(viewLifecycleOwner, Observer { habits ->
            habitAdapter.setHabits(habits)
        })

        viewModel.stringForFilter.observe(viewLifecycleOwner, Observer { filterString ->
            habitAdapter.filter(filterString)
        })

        viewModel.sortBy.observe(viewLifecycleOwner, Observer { sortBy ->
            if (viewModel.getSortType() == SORT_FROM_BIGGEST) habitAdapter.sortFromBiggest(sortBy)
            else habitAdapter.sortFromSmallest(sortBy)
        })

        addHabitButton.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container).navigate(R.id.dataInputFragment)
        }

        bottomSheet.setOnClickListener {
            BottomSheetFragment().show(requireActivity().supportFragmentManager, null)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_FOR_SAVING_TYPE_FILTER, typeFilterValue)
    }
}
