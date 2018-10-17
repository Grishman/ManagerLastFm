package lastfm.grishman.com.lastfmapp.di

import androidx.room.Room
import lastfm.grishman.com.lastfmapp.BuildConfig
import lastfm.grishman.com.lastfmapp.db.LastFmDb
import lastfm.grishman.com.lastfmapp.di.DatasourceProperties.SERVER_URL
import lastfm.grishman.com.lastfmapp.network.AddAPIKeyInterceptor
import lastfm.grishman.com.lastfmapp.network.LastFmService
import lastfm.grishman.com.lastfmapp.ui.albumDetails.AlbumDetailViewModel
import lastfm.grishman.com.lastfmapp.ui.albumDetails.DetailsRepository
import lastfm.grishman.com.lastfmapp.ui.mainScreen.AlbumsRepository
import lastfm.grishman.com.lastfmapp.ui.mainScreen.MainScreenViewModel
import lastfm.grishman.com.lastfmapp.ui.search.SearchRepository
import lastfm.grishman.com.lastfmapp.ui.search.SearchViewModel
import lastfm.grishman.com.lastfmapp.ui.topAlbums.TopAlbumsRepository
import lastfm.grishman.com.lastfmapp.ui.topAlbums.TopAlbumsViewModel
import lastfm.grishman.com.lastfmapp.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory.create
import java.util.concurrent.TimeUnit

/**
 * DI for the app.
 */
val appModule = module {
    // single instance of Repositories
    single { AlbumsRepository(get(), get<LastFmDb>().albumDao()) }

    single { SearchRepository(get()) }

    single { TopAlbumsRepository(get(), get<LastFmDb>().albumDao()) }

    single { DetailsRepository(get(), get<LastFmDb>().detailsDao()) }

    // provided OkHttp client.
    single { createOkHttpClient() }

    // Create server API.
    single { createWebService<LastFmService>(get(), SERVER_URL) }

    //Room DB
    single(definition = {
        Room.databaseBuilder(androidApplication(), LastFmDb::class.java, Constants.DB_NAME)
                .build()
    })

    // Saved albums ViewModel
    viewModel { MainScreenViewModel(get()) }

    //Search artist ViewModel
    viewModel { SearchViewModel(get()) }

    //Top albums ViewModel
    viewModel { TopAlbumsViewModel(get()) }

    //Album info ViewModel
    viewModel { AlbumDetailViewModel(get()) }
}

object DatasourceProperties {
    const val SERVER_URL = BuildConfig.BASE_API_URL
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(AddAPIKeyInterceptor(BuildConfig.SECRET_KEY))
            .addInterceptor(httpLoggingInterceptor)
            .build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}