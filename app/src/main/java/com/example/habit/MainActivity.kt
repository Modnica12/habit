package com.example.habit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.*
import androidx.viewpager.widget.ViewPager
import com.example.habit.adapters.HabitsListViewPagerAdapter
import com.example.habit.fragments.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet.*

class MainActivity : AppCompatActivity(), Communicator, NavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(LOG_DEBUG, "ONCREATE")

        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager>(R.id.habitsViewPager)
        val adapter = HabitsListViewPagerAdapter(supportFragmentManager)

        nav_view.setNavigationItemSelectedListener(this)

        viewPager.adapter = adapter
        habitsTabLayout.setupWithViewPager(viewPager)

        addHabitButton.setOnClickListener {
            //startNewFragment(DataInputFragment())
            DataInputFragment().show(supportFragmentManager, "Create Habit")
        }

        /*val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        bottomSheetBehavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })*/

    }

    override fun passData(habit: Habit, id: Int) {
        val bundle = Bundle()
        bundle.putSerializable(KEY_FOR_HABIT, habit)
        bundle.putInt(ID_KEY, id)

        val secondFragment = DataInputFragment()
        secondFragment.arguments = bundle
        secondFragment.show(supportFragmentManager, "Change Habit")

        //добавить элементы в стек:
        // transaction.addToBackStack(null)

        //удаляем предыдущие фаргменты:
        // this.supportFragmentManager.popBackStack()

    }

    override fun startNewFragment(fragment: Fragment) {
        val transaction = this.supportFragmentManager.beginTransaction()

        transaction.replace(R.id.tabAndViewPager, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val communicator = this as Communicator

        nav_view.bringToFront()
        nav_view.requestLayout()

        if (id == R.id.nav_home){
            supportFragmentManager.popBackStack()
        }
        else if (id == R.id.nav_about){
            communicator.startNewFragment(AboutFragment())
        }
        mainActivity.closeDrawer(GravityCompat.START)
        return true
    }


}

interface Communicator{
    fun passData(habit: Habit, id: Int)
    fun startNewFragment(fragment: Fragment)
}
