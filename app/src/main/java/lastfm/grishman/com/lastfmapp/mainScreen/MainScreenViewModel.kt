package lastfm.grishman.com.lastfmapp.mainScreen

import android.arch.lifecycle.ViewModel
import lastfm.grishman.com.lastfmapp.network.ApiRepo

class MainScreenViewModel(private val repo: ApiRepo) : ViewModel() {

    fun getGreating() {
        repo.getGreat()
    }
}
