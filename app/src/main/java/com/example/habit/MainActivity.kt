package com.example.habit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.fragment.app.*
import com.example.habit.fragments.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Communicator, NavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(LOG_DEBUG, "ONCREATE")

        setContentView(R.layout.activity_main)

        nav_view.setNavigationItemSelectedListener(this)

        // если только открыли приложение создаем вью пейджер
        if (savedInstanceState == null)
            addFragment(ListAndPagerFragment())

    }

    override fun passDataInDataInputFragment(id: Int) {
        // создаем новый фрагмент и передаем в него данные
        val bundle = Bundle()
        bundle.putInt(KEY_FOR_HABIT, id)

        val transaction = this.supportFragmentManager.beginTransaction()
        val secondFragment = DataInputFragment()
        secondFragment.arguments = bundle

        transaction.add(R.id.fragment_container, secondFragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }

    override fun startNewFragment(fragment: Fragment) {
        // создаем новыйй фрагмент
        val transaction = this.supportFragmentManager.beginTransaction()

        transaction.add(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun addFragment(fragment: Fragment) {
        // добавляем фрагмент
        val transaction = this.supportFragmentManager.beginTransaction()

        transaction.add(R.id.fragment_container, fragment)
        transaction.commit()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // обработка дровера
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
    fun passDataInDataInputFragment(id: Int)
    fun startNewFragment(fragment: Fragment)
    fun addFragment(fragment: Fragment)
}
