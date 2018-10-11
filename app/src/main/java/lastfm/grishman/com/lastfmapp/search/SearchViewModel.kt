package lastfm.grishman.com.lastfmapp.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import io.reactivex.disposables.CompositeDisposable
import lastfm.grishman.com.lastfmapp.extensions.toLiveData
import lastfm.grishman.com.lastfmapp.model.SearchResult
import lastfm.grishman.com.lastfmapp.network.Outcome

class SearchViewModel(private val repo: SearchRepository) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    var isRefreshing: ObservableBoolean = ObservableBoolean(false)

    val searchOutcome: LiveData<Outcome<SearchResult>> by lazy {
        //Convert publish subject to livedata
        repo.searchArtistResult.toLiveData(compositeDisposable)
    }

    fun search(artistToSearch: String) {
        repo.searchArtist(artistToSearch)
    }

    override fun onCleared() {
        super.onCleared()
        //clear the disposables when the viewmodel is cleared
        compositeDisposable.clear()
    }
}
