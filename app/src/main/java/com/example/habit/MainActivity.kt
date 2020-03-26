package com.example.habit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.fragment.app.*
import androidx.viewpager.widget.ViewPager
import com.example.habit.adapters.HabitsListViewPagerAdapter
import com.example.habit.fragments.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Communicator, NavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager>(R.id.habitsViewPager)
        val adapter = HabitsListViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(GoodHabitsListFragment(), "good")
        adapter.addFragment(BadHabitsListFragment(), "bad")

        nav_view.setNavigationItemSelectedListener(this)

        viewPager.adapter = adapter
        habitsTabLayout.setupWithViewPager(viewPager)
    }

    override fun passData(habit: Habit, id: Int) {
        val bundle = Bundle()
        bundle.putSerializable(KEY_FOR_HABIT, habit)
        bundle.putInt(ID_KEY, id)

        val transaction = this.supportFragmentManager.beginTransaction()
        val secondFragment = DataInputFragment()
        secondFragment.arguments = bundle

        transaction.replace(R.id.mainActivity, secondFragment)

        //добавить элементы в стек:
        // transaction.addToBackStack(null)

        //удаляем предыдущие фаргменты:
        // this.supportFragmentManager.popBackStack()

        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }

    override fun startNewFragment(fragment: Fragment) {
        val transaction = this.supportFragmentManager.beginTransaction()
        val secondFragment = DataInputFragment()

        transaction.replace(R.id.tabAndViewPager, fragment)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val communicator = this as Communicator

        nav_view.bringToFront()
        nav_view.requestLayout()

        if (id == R.id.nav_home){
            supportFragmentManager.popBackStack()
            mainActivity.closeDrawer(GravityCompat.START)
        }
        else if (id == R.id.nav_about){
            communicator.startNewFragment(AboutFragment())
            mainActivity.closeDrawer(GravityCompat.START)
        }
        return true
    }

}

interface Communicator{
    fun passData(habit: Habit, id: Int)
    fun startNewFragment(fragment: Fragment)
}
