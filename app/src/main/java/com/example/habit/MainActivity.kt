package com.example.habit

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.content.getSystemService
import androidx.core.view.GravityCompat
import androidx.fragment.app.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.habit.fragments.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_header.*
import kotlinx.android.synthetic.main.drawer_header.view.*

class MainActivity : AppCompatActivity(), Communicator, NavigationView.OnNavigationItemSelectedListener{

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(LOG_DEBUG, "ONCREATE")

        setContentView(R.layout.activity_main)

        Glide.with(this)
            .load("https://i.pinimg.com/originals/0c/a9/e2/0ca9e28dcb12dc698cfd2beda6d6fa64.jpg")
            .error(R.drawable.image_not_found)
            .placeholder(R.drawable.image_placeholder)
            .transform(CircleCrop())
            .into(nav_view.getHeaderView(0).avatar)

        nav_view.setNavigationItemSelectedListener(this)

        // если только открыли приложение создаем вью пейджер
        if (savedInstanceState == null)
            addFragment(ListAndPagerFragment())
    }

    override fun passDataInDataInputFragment(uid: String) {
        // создаем новый фрагмент и передаем в него данные
        val bundle = Bundle()
        bundle.putString(KEY_FOR_HABIT, uid)

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

        val imageSize = (64 * applicationContext.resources.displayMetrics.density).toInt()
        return true
    }

}

interface Communicator{
    fun passDataInDataInputFragment(uid: String)
    fun startNewFragment(fragment: Fragment)
    fun addFragment(fragment: Fragment)
}
