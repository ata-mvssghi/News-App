package com.example.project.adapters

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.project.MainActivity
import com.example.project.MainFragment
import com.example.project.api.ApiService

class ViewPagerAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 6
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = MainFragment()
        val bundle = createBundle(position)
        fragment.arguments = bundle

        return fragment
    }

    private fun createBundle(position: Int): Bundle {
        val bundle = Bundle()
        val section = when (position) {
            0 -> null
            1 -> "world"
            2 -> "sport"
            3 -> "business"
            4 -> "science"
            5 -> "society"
            else -> null
        }
        bundle.putString("section", section)
        return bundle
    }
}