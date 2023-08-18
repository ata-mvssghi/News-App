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
import com.example.project.api.ApiService.Companion.businessLastKey
import com.example.project.api.ApiService.Companion.generalLastKey
import com.example.project.api.ApiService.Companion.page
import com.example.project.api.ApiService.Companion.scienceLastKey
import com.example.project.api.ApiService.Companion.societyLastKey
import com.example.project.api.ApiService.Companion.sportLastKey
import com.example.project.api.ApiService.Companion.worldLAstKey
import kotlin.text.Typography.section

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
        pageHandler(section)
        return bundle
    }
    fun pageHandler(section:String?){
       when(section) {
           null-> {
               if (generalLastKey == 0)
                   page = 1
               else
                   page = generalLastKey
           }
           "world"-> {
                   if (worldLAstKey == 0)
                       page = 1
                   else
                       page = worldLAstKey
           }
           "sport"->{
               if(sportLastKey==0)
                  page=1
               else
                  page= sportLastKey
           }
           "business"->{
               if(businessLastKey==0)
                 page=1
               else {
                   page= businessLastKey
               }
           }
           "science"->{
               if(scienceLastKey==0)
                   page=1
               else
                   page= scienceLastKey
           }
           "society"->{
               if(societyLastKey==0)
                   page=1
               else
                   page= societyLastKey
           }
       }
    }
}