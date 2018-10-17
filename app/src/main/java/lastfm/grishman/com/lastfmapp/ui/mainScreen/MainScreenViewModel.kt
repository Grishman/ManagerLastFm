package lastfm.grishman.com.lastfmapp.ui.mainScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import lastfm.grishman.com.lastfmapp.vo.ViewAlbum

class MainScreenViewModel(private val repo: AlbumsRepository) : ViewModel() {

    fun getAlbums(): LiveData<List<ViewAlbum>> {
        return repo.loadFromDb()
    }

    fun removeAlbum(album: ViewAlbum) {
        repo.removeAlbum(album)
    }
}
