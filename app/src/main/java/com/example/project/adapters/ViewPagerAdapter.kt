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

class ViewPagerAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 6
    }

    override fun createFragment(position: Int): Fragment {
        val myINstance:MainFragment
        myINstance= MainFragment()
        val bundle=Bundle()
        when(position){
            0->{
                bundle.putString("ha", null)
                myINstance.arguments = bundle
            }
           1-> {
               Log.i("info","navigator is working")
               bundle.putString("ha", "world")
               myINstance.arguments = bundle
           }
           2-> {
               Log.i("info","navigator is working")
               bundle.putString("ha","sport")
               myINstance.arguments=bundle
           }
          3->{
               Log.i("info","navigator is working")
               bundle.putString("ha","business")
               myINstance.arguments=bundle
           }
          4->{
               Log.i("info","navigator is working")
               bundle.putString("ha","science")
               myINstance.arguments=bundle
           }
          5->{
              Log.i("info","navigator is working")
              bundle.putString("ha","society")
              myINstance.arguments=bundle
          }
        }
        return myINstance
    }
}