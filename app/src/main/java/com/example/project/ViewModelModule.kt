import com.example.project.api.ApiService
import com.example.project.local.NewsDataBase
import com.example.project.remote.NewsRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlin.text.Typography.section

@Module
@InstallIn(ViewModelComponent::class) // Install in ViewModelComponent
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideRepository(
        section:String?,
        beerApi: ApiService,
        beerDb: NewsDataBase
    ): NewsRemoteMediator {
        return NewsRemoteMediator(section,beerDb, beerApi)
    }


}
