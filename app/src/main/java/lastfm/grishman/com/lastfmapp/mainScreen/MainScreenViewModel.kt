package lastfm.grishman.com.lastfmapp.mainScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import lastfm.grishman.com.lastfmapp.vo.ViewAlbum

class MainScreenViewModel(private val repo: ApiRepo) : ViewModel() {

    fun getGreating() {
        repo.getGreat()
    }

    fun getAlbums(): LiveData<List<ViewAlbum>> {
        return repo.loadFromDb()
    }

    fun removeAlbum(album: ViewAlbum) {
        repo.removeAlbum(album)
    }
}
