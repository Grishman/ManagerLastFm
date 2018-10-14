package lastfm.grishman.com.lastfmapp.topAlbums

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import io.reactivex.disposables.CompositeDisposable
import lastfm.grishman.com.lastfmapp.extensions.toLiveData
import lastfm.grishman.com.lastfmapp.model.albums.Album
import lastfm.grishman.com.lastfmapp.network.Outcome

class TopAlbumsViewModel(private val repo: TopAlbumsRepository) : ViewModel() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    var isRefreshing: ObservableBoolean = ObservableBoolean(false)

    val searchOutcome: LiveData<Outcome<List<Album>>> by lazy {
        //Convert publish subject to livedata
        repo.albumsResult.toLiveData(compositeDisposable)
    }
    //todo add getter with null checks

    fun search(artistToSearch: String, mbid: String) {
        repo.getTopAlbums(artistToSearch, mbid)
    }

    fun saveAlbum(album: Album) {
        repo.saveAlbumTest(album = album)
    }

    override fun onCleared() {
        super.onCleared()
        //clear the disposables when the viewmodel is cleared
        compositeDisposable.clear()
    }
}
