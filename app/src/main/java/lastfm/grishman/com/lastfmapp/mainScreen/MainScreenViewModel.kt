package lastfm.grishman.com.lastfmapp.mainScreen

import android.arch.lifecycle.ViewModel

class MainScreenViewModel(private val repo: ApiRepo) : ViewModel() {

    fun getGreating() {
        repo.getGreat()
    }
}
