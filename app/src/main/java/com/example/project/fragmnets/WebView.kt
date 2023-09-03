package com.example.project.fragmnets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.paging.LOGGER
import com.bumptech.glide.load.engine.executor.GlideExecutor.UncaughtThrowableStrategy.LOG
import com.example.project.R
import com.example.project.databinding.FragmentWebViewBinding
import com.example.project.domain.NewsFeed

class WebView : Fragment() {
    lateinit var binding:FragmentWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentWebViewBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val receivedObject: NewsFeed? =
            requireArguments().getSerializable("news") as NewsFeed?
        val url=receivedObject?.webUrl?:"google.com"
        if(url=="google.com")
            Log.i("lll","oseeeen")
        binding.webView.apply {
            webViewClient=WebViewClient()
            loadUrl(url)
        }
    }

}