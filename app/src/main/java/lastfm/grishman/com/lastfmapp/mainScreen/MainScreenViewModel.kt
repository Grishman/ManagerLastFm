package lastfm.grishman.com.lastfmapp.mainScreen

import androidx.lifecycle.ViewModel
import lastfm.grishman.com.lastfmapp.model.albums.Album

class MainScreenViewModel(private val repo: ApiRepo) : ViewModel() {

    fun getGreating() {
        repo.getGreat()
    }

//    fun getAlbums(): LiveData<List<Album>> {
//        //return repo.loadFromDb()
//        return
//    }

    fun removeAlbum(album: Album) {
        repo.removeAlbum(album)
    }
}
