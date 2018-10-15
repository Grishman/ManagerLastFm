package lastfm.grishman.com.lastfmapp.mainScreen

import android.util.Log
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import lastfm.grishman.com.lastfmapp.db.AlbumDao
import lastfm.grishman.com.lastfmapp.model.SearchResult
import lastfm.grishman.com.lastfmapp.model.albums.Album
import lastfm.grishman.com.lastfmapp.network.LastFmService

class ApiRepo(private val api: LastFmService, private val albumDao: AlbumDao) {

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

//    fun loadFromDb(): LiveData<List<Album>> {
//        //return albumDao.getAll()
//    }

    fun removeAlbum(album: Album) {
        Completable.fromAction {
            //albumDao.delete(album)
        }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

}
