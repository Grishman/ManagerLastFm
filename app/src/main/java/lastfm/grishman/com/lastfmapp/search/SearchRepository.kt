package lastfm.grishman.com.lastfmapp.search

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import lastfm.grishman.com.lastfmapp.extensions.addTo
import lastfm.grishman.com.lastfmapp.extensions.failed
import lastfm.grishman.com.lastfmapp.extensions.loading
import lastfm.grishman.com.lastfmapp.extensions.success
import lastfm.grishman.com.lastfmapp.model.SearchResult
import lastfm.grishman.com.lastfmapp.network.LastFmService
import lastfm.grishman.com.lastfmapp.network.Outcome

class SearchRepository(private val api: LastFmService) {

    private val disposable: CompositeDisposable = CompositeDisposable()

    val searchArtistResult: PublishSubject<Outcome<SearchResult>> =
            PublishSubject.create<Outcome<SearchResult>>()

    fun searchArtist(artist: String) {
        searchArtistResult.loading(true)

        api.searchArtist(artist = artist)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result: SearchResult -> searchArtistResult.success(result) },
                        { error: Throwable -> handleError(error) }
                ).addTo(disposable)


    }


    fun handleError(error: Throwable) {
        searchArtistResult.failed(error)
    }
}
