package com.example.project

import AppModule.provideNewsApi
import AppModule.provideNewsDataBase
import AppModule.provideNewsPager
import ViewModelFactory
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
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
import androidx.navigation.fragment.findNavController
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
import com.example.project.fragmnets.SettingsFragment
import com.example.project.fragmnets.onApiSettingChangedListner
import com.example.project.local.NewsDataBase
import com.example.project.local.NewsEntity
import com.example.project.viewModel.NEwsViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.text.Typography.section

class MainFragment : Fragment(),onApiSettingChangedListner {
    private lateinit var viewModel: NEwsViewModel
    lateinit var category: String
    lateinit var adapter: NewsAdpater
    lateinit var binding:FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding=FragmentMainBinding.inflate(inflater)
        var mysection:String?="initial value"
        this.arguments?.let {
            category= it.getString("section","some default value")
            Log.i("info","key is ${category}")
            mysection=category
            if (category=="some default value"){
                mysection=null
                }
        }
        //getting data from shared preference to pass to the api
        val sp =PreferenceManager.getDefaultSharedPreferences(requireContext())
        val time:Long=sp.getLong("date_preference",1)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate = dateFormat.format(Date(time))
        val order=sp.getString("order_by","newest")

        //setting the api cahnge listener instance to this
        SettingsFragment.ApiChangedInstanceForFragment.apiChangeListenerFrag=this
        val pager: Pager<Int, NewsEntity> = provideNewsPager(provideNewsDataBase(binding.root.context), provideNewsApi(),mysection, formattedDate,order)
        viewModel = ViewModelProvider(this,ViewModelFactory(pager))[NEwsViewModel::class.java]
         adapter = NewsAdpater()
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
        binding.swipeRefresh.setOnRefreshListener{
            adapter.refresh()
            Log.i("remote","swipe refresh is doing well")
            binding.swipeRefresh.isRefreshing=false
        }
        return binding.root
    }

    override fun onUpdate(newDate: String, newOrder: String?) {
        adapter.refresh()
        Log.i("remote","$$% adapter's refresh called in main fragment order is $newOrder and the date is $newDate")
    }
    override fun onResume() {
        super.onResume()
        Log.i("remote","on resume of fragment main called")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.setOnItemClickListener {
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.webUrl))
//            binding.root.context.startActivity(intent)
            val bundle=Bundle()
            bundle.putSerializable("news",it)
            findNavController().navigate(R.id.action_primaryFragment_to_webView,bundle)
            }
        }
    }