import com.example.project.api.ApiService
import com.example.project.local.NewsDataBase
import com.example.project.remote.NewsRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class) // Install in ViewModelComponent
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideRepository(
        beerApi: ApiService,
        beerDb: NewsDataBase
    ): NewsRemoteMediator {
        return NewsRemoteMediator(beerDb, beerApi)
    }


}
