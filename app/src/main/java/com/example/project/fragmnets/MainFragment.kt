package com.example.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.project.adapters.ViewPagerAdapter
import com.example.project.databinding.FragmentPrimaryBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
class mms : Fragment() {
    val tabsArray = arrayOf("GENERAL","WORLD", "SPORTS", "BUSINESS","SCIENCE","SOCIETY")
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding=FragmentPrimaryBinding.inflate(inflater)
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
        return binding.root
    }

}