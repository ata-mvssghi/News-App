package com.example.project

import AppModule.provideBeerApi
import AppModule.provideBeerDatabase
import AppModule.provideBeerPager
import ViewModelFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.Pager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.project.adapters.NewsAdpater
import com.example.project.adapters.ViewPagerAdapter
import com.example.project.databinding.FragmentMainBinding
import com.example.project.databinding.FragmentPrimaryBinding
import com.example.project.local.NewsEntity
import com.example.project.viewModel.NEwsViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private lateinit var viewModel: NEwsViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=FragmentMainBinding.inflate(inflater)
        val pager: Pager<Int, NewsEntity> = provideBeerPager(provideBeerDatabase(binding.root.context), provideBeerApi())
        viewModel = ViewModelProvider(this,ViewModelFactory(pager))[NEwsViewModel::class.java]

        Log.i("pahoo","main")
        val adapter = NewsAdpater()
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager= LinearLayoutManager(binding.root.context)
        lifecycleScope.launch {
            viewModel.beerPagingFlow.collectLatest { pagingData ->
                adapter.submitData(lifecycle, pagingData)
            }
        }
        return binding.root

    }

}