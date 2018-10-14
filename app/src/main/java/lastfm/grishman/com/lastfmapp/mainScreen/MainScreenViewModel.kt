package lastfm.grishman.com.lastfmapp.mainScreen

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import lastfm.grishman.com.lastfmapp.model.albums.Album

class MainScreenViewModel(private val repo: ApiRepo) : ViewModel() {

    fun getGreating() {
        repo.getGreat()
    }

    fun getAlbums() :LiveData<List<Album>>{
        return repo.loadFromDb()
    }
}
