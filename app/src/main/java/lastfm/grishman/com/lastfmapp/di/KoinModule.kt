package lastfm.grishman.com.lastfmapp.di

import lastfm.grishman.com.lastfmapp.BuildConfig
import lastfm.grishman.com.lastfmapp.di.DatasourceProperties.SERVER_URL
import lastfm.grishman.com.lastfmapp.mainScreen.ApiRepo
import lastfm.grishman.com.lastfmapp.mainScreen.MainScreenViewModel
import lastfm.grishman.com.lastfmapp.network.AddAPIKeyInterceptor
import lastfm.grishman.com.lastfmapp.network.LastFmService
import lastfm.grishman.com.lastfmapp.search.SearchRepository
import lastfm.grishman.com.lastfmapp.search.SearchViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory.create
import java.util.concurrent.TimeUnit

/**
 * DI stuff
 */
val appModule = module {
    // single instance of HelloRepository
    single { ApiRepo(get()) }

    single { SearchRepository(get()) }

    // provided web components
    single { createOkHttpClient() }

    // Fill property
    single { createWebService<LastFmService>(get(), SERVER_URL) }

    // MyViewModel ViewModel
    viewModel { MainScreenViewModel(get()) }

    viewModel { SearchViewModel(get()) }
}

//val remoteDatasourceModule = applicationContext {
//    // provided web components
//    bean { createOkHttpClient() }
//
//    // Fill property
//    bean { createWebService<WeatherDatasource>(get(), getProperty(SERVER_URL)) }
//}

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