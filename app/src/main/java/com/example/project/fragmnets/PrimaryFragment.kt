package com.example.project

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentActivity
import androidx.preference.PreferenceManager
import androidx.viewpager2.widget.ViewPager2
import com.example.project.adapters.ViewPagerAdapter
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
class PrimaryFragment : Fragment() {
    val tabsArray = arrayOf("GENERAL","WORLD", "SPORTS", "BUSINESS","SCIENCE","SOCIETY")
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding=com.example.project.databinding.FragmentPrimaryBinding.inflate(inflater)
        drawerLayout = binding.drawerLayout;
        viewPager = binding.viewPager;
        tabLayout = binding.tabLayout;
        val my_adapter = ViewPagerAdapter(
            childFragmentManager,
            lifecycle
        )
        viewPager.adapter = my_adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabsArray[position]
        }.attach()

        val navView: NavigationView = binding.navView
        navView.setNavigationItemSelectedListener {
            it.isChecked = true
            when (it.itemId) {
                R.id.general->{
                    viewPager.currentItem=0
                }
                R.id.world->{
                    viewPager.currentItem=1
                }
                R.id.sports->{
                    viewPager.currentItem=2
                }
                R.id.business->{
                    viewPager.currentItem=3
                }
                R.id.science->{
                    viewPager.currentItem=4
                }
                R.id.society->{
                    viewPager.currentItem=5
                }
            }
            drawerLayout.closeDrawers()
            true
        }
        // setting a toggle bar for navigation drawer
        val toolBar=binding.toolbar
        val toggleButton = ActionBarDrawerToggle(
            requireActivity(), // or activity
            drawerLayout,
            toolBar,
            R.string.menu_opened,
            R.string.menu_closed
        )

        toggleButton.syncState()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Log.i("remote", "on resume of primary fragment called")
    }

}