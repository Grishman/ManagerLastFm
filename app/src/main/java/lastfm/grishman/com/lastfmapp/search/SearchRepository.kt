package lastfm.grishman.com.lastfmapp.search

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import lastfm.grishman.com.lastfmapp.model.SearchResult
import lastfm.grishman.com.lastfmapp.network.LastFmService

class SearchRepository(private val api: LastFmService) {

    val disposable: CompositeDisposable = CompositeDisposable()

    fun searchArtist(artist: String) {
        disposable.add(
                api.searchArtist(artist = artist)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result: SearchResult? -> Log.d("sk", result?.toString()) },
                                { error: Throwable? -> }
                        )
        )

    }

}
