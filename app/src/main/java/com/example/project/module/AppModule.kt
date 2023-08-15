import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.project.api.ApiService
import com.example.project.local.NewsDataBase
import com.example.project.local.NewsEntity
import com.example.project.remote.NewsRemoteMediator
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBeerDatabase(@ApplicationContext context: Context): NewsDataBase {
        return Room.databaseBuilder(
            context,
            NewsDataBase::class.java,
            "newsDatabase.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideBeerApi(): ApiService {
        return Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideBeerPager(newsDb: NewsDataBase, newsApi:ApiService ): Pager<Int, NewsEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = NewsRemoteMediator(
                newsDb = newsDb,
                newsApi = newsApi
            ),
            pagingSourceFactory = {
                newsDb.dao.pagingSource()
            }
        )
    }
}
