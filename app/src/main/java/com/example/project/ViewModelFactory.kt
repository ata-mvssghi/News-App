import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.paging.Pager
import com.example.project.local.NewsEntity
import com.example.project.viewModel.NEwsViewModel

class ViewModelFactory(private val pager: Pager<Int, NewsEntity>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NEwsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NEwsViewModel(pager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
