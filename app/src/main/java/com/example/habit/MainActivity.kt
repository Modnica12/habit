package com.example.habit

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habit.fragments.DataInputFragment
import com.example.habit.fragments.HabitsListFragment
import com.example.habit.fragments.ID_KEY
import com.example.habit.fragments.KEY_FOR_HABIT
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Communicator{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun passData(habit: Habit, id: Int) {
        val bundle = Bundle()
        bundle.putSerializable(KEY_FOR_HABIT, habit)
        bundle.putInt(ID_KEY, id)

        val transaction = this.supportFragmentManager.beginTransaction()
        val secondFragment = DataInputFragment()
        secondFragment.arguments = bundle

        transaction.replace(R.id.mainActivity, secondFragment)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }

    override fun passData2(habit: Habit, id: Int) {
        val bundle = Bundle()
        bundle.putSerializable(KEY_FOR_HABIT, habit)
        bundle.putInt(ID_KEY, id)

        val transaction = this.supportFragmentManager.beginTransaction()
        val secondFragment = HabitsListFragment()
        secondFragment.arguments = bundle

        transaction.replace(R.id.mainActivity, secondFragment)

        //добавить элементы в стек:
        // transaction.addToBackStack(null)

        //удаляем предыдущие фаргменты:
        // this.supportFragmentManager.popBackStack()

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }

    override fun startNewFragment() {
        val transaction = this.supportFragmentManager.beginTransaction()
        val secondFragment = DataInputFragment()

        transaction.replace(R.id.mainActivity, secondFragment)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }

}

interface Communicator{
    fun passData(habit: Habit, id: Int)
    fun passData2(habit: Habit, id: Int)
    fun startNewFragment()
}
