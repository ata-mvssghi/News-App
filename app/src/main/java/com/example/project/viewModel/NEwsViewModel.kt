package com.example.project.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.project.local.NewsEntity
import com.example.project.mappers.toNewsFeed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class NEwsViewModel @Inject constructor(
    pager: Pager<Int, NewsEntity>
): ViewModel() {

    val beerPagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toNewsFeed() }
        }
        .cachedIn(viewModelScope)
}