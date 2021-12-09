package com.example.palfinder.views.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.palfinder.R
import com.example.palfinder.views.HomeActivity
import com.example.palfinder.views.user.account.InitialAccountViewPagerAdapter
import com.example.palfinder.views.user.account.InitialGroupSelectionFragment
import com.example.palfinder.views.user.account.InitialSetupConfirmationFragment
import com.example.palfinder.views.user.account.InitialTagSelectionFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.bottom_navigation
import kotlinx.android.synthetic.main.activity_initial_account_setup.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_search_user.view.*

class SearchActivity : AppCompatActivity(R.layout.activity_search) {
    private val steps = arrayListOf(
        Pair(R.id.mnuUsers, SearchUsersFragment()),
        Pair(R.id.mnuEvents, SearchEventsFragment()),
        Pair(R.id.mnuGroups, SearchGroupsFragment()),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeQuery()
        setupViewPager()
        setupNavigation()
    }

    private fun observeQuery() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                SearchData.setSearchTerm(p0.toString())
            }
        })
    }

    private fun setupNavigation() {
        toggleNavigationButton(R.id.mnuUsers)
        bottom_navigation.setOnItemSelectedListener { item ->
            toggleNavigationButton(item.itemId)
            vpSearchFragments.setCurrentItem(steps.map {data -> data.first}.indexOf(item.itemId), true)
            true
        }
    }

    private fun toggleNavigationButton(id: Int) {
        bottom_navigation.menu.forEach {
            it.isEnabled = it.itemId != id
        }
    }

    private fun setupViewPager() {
        vpSearchFragments.adapter = ViewPagerAdapter(
            steps.map { data -> data.second }  as ArrayList<Fragment>,
            supportFragmentManager,
            lifecycle
        )
        vpSearchFragments.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bottom_navigation.selectedItemId = steps.map { data -> data.first }[position]
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(applicationContext, HomeActivity::class.java))
        finish()
    }

    inner class ViewPagerAdapter(
        list: ArrayList<Fragment>,
        fm: FragmentManager,
        lifecycle: Lifecycle
    ) : FragmentStateAdapter(fm, lifecycle) {

        private val fragmentList = list

        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }
    }
}

