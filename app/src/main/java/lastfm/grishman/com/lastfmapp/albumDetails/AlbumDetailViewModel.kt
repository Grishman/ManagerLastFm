package lastfm.grishman.com.lastfmapp.albumDetails

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import lastfm.grishman.com.lastfmapp.extensions.toLiveData
import lastfm.grishman.com.lastfmapp.model.album.DetailedAlbum
import lastfm.grishman.com.lastfmapp.network.Outcome

class AlbumDetailViewModel(private val repo: DetailsRepository) : ViewModel() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    var isRefreshing: ObservableBoolean = ObservableBoolean(false)

    val searchOutcome: LiveData<Outcome<DetailedAlbum>> by lazy {
        //Convert publish subject to livedata
        repo.albumsResult.toLiveData(compositeDisposable)
    }
    //todo add getter with null checks

    fun search(artistToSearch: String, mbid: String?, albumName: String) {
        repo.getAlbumInfo(artistToSearch, albumName, mbid?:"")
    }

    override fun onCleared() {
        super.onCleared()
        //clear the disposables when the viewmodel is cleared
        compositeDisposable.clear()
    }
}
