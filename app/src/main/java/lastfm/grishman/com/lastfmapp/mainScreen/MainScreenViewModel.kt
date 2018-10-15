package lastfm.grishman.com.lastfmapp.mainScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import lastfm.grishman.com.lastfmapp.model.albums.Album

class MainScreenViewModel(private val repo: ApiRepo) : ViewModel() {

    fun getGreating() {
        repo.getGreat()
    }

    fun getAlbums(): LiveData<List<Album>> {
        return repo.loadFromDb()
    }

    fun removeAlbum(album: Album) {
        repo.removeAlbum(album)
    }
}
