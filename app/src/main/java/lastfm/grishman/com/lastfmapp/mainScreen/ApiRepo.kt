package lastfm.grishman.com.lastfmapp.mainScreen

import android.util.Log
import androidx.lifecycle.LiveData
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import lastfm.grishman.com.lastfmapp.db.AlbumDao
import lastfm.grishman.com.lastfmapp.db.AlbumWithTracksDao
import lastfm.grishman.com.lastfmapp.model.SearchResult
import lastfm.grishman.com.lastfmapp.network.LastFmService
import lastfm.grishman.com.lastfmapp.vo.ViewAlbum

class ApiRepo(private val api: LastFmService, private val albumDao: AlbumDao,private val tracksDao: AlbumWithTracksDao) {

    val disposable: CompositeDisposable = CompositeDisposable()
    fun getGreat() {
        disposable.add(
                api.searchArtist("kevin")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result: SearchResult? -> Log.d("sk", result?.toString()) },
                                { error: Throwable? -> }
                        )
        )

    }

    fun loadFromDb(): LiveData<List<ViewAlbum>> {
        //tracksDao.getAll().value
        return albumDao.getAll()
    }

    fun removeAlbum(album: ViewAlbum) {
        Completable.fromAction {
            albumDao.delete(album)
        }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

}
