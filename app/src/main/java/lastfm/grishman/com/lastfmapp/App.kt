package lastfm.grishman.com.lastfmapp

import android.app.Application
import lastfm.grishman.com.lastfmapp.di.appModule
import org.koin.android.ext.android.startKoin

/**
 * App class
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        // Start Koin DI
        startKoin(this, listOf(appModule))
    }
}