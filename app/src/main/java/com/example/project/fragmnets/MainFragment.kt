package com.example.project

import AppModule.provideNewsApi
import AppModule.provideNewsDataBase
import AppModule.provideNewsPager
import ViewModelFactory
import android.content.res.Configuration
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
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.Pager
import androidx.paging.RemoteMediator
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.project.adapters.NewsAdpater
import com.example.project.adapters.ViewPagerAdapter
import com.example.project.api.ApiService
import com.example.project.databinding.FragmentMainBinding
import com.example.project.databinding.FragmentPrimaryBinding
import com.example.project.local.NewsDataBase
import com.example.project.local.NewsEntity
import com.example.project.viewModel.NEwsViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.text.Typography.section

class MainFragment : Fragment() {
    private lateinit var viewModel: NEwsViewModel
    lateinit var category: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=FragmentMainBinding.inflate(inflater)
        var mysection:String?="initial value"
        this.arguments?.let {
            category= it.getString("section","some default value")
            Log.i("info","key is ${category}")
            mysection=category
            if (category=="some default value"){
                mysection=null
                }

        }
        val pager: Pager<Int, NewsEntity> = provideNewsPager(provideNewsDataBase(binding.root.context), provideNewsApi(),mysection)
        viewModel = ViewModelProvider(this,ViewModelFactory(pager))[NEwsViewModel::class.java]
        val adapter = NewsAdpater()
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager= LinearLayoutManager(binding.root.context)
        lifecycleScope.launch {
            viewModel.newsPagingFlow.collectLatest { pagingData ->
                adapter.submitData(lifecycle, pagingData)
            }
        }
        // setting navigation logic
        binding.floatingActionButton2.setOnClickListener {
            it.findNavController().navigate(R.id.action_primaryFragment_to_settingsFragment)
        }
        return binding.root
    }




    override fun onResume() {
        super.onResume()
        Log.i("remote","on resume of fragment main called")
    }


}