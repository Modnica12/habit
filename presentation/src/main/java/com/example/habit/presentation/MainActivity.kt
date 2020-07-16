package com.example.habit.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.*
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.data.KEY_FOR_HABIT
import com.example.data.LOG_DEBUG
import com.example.habit.R
import com.example.habit.presentation.fragments.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_fragment.*
import kotlinx.android.synthetic.main.drawer_header.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private lateinit var navigationController: NavController

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        nav_view.setNavigationItemSelectedListener(this)

        navigationController = Navigation.findNavController(this, R.id.nav_host_fragment_container)

        Glide.with(this)
            .load("https://i.pinimg.com/originals/0c/a9/e2/0ca9e28dcb12dc698cfd2beda6d6fa64.jpg")
            .error(R.drawable.image_not_found)
            .placeholder(R.drawable.image_placeholder)
            .transform(CircleCrop())
            .into(nav_view.getHeaderView(0).avatar)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        mainActivity.closeDrawers()
        return when (item.itemId) {
            R.id.nav_main -> {
                navigationController.navigate(R.id.listAndPagerFragment, null, popUpBehavior)
                true
            }
            R.id.nav_about -> {
                navigationController.navigate(R.id.aboutFragment, null, popUpBehavior)
                true
            }
            else -> {
                Toast.makeText(this, "Smth went wrong", Toast.LENGTH_SHORT).show()
                false
            }
        }
    }

    companion object{
        val popUpBehavior = NavOptions.Builder().setPopUpTo(R.id.main_nav_graph, true).build()
    }

}
