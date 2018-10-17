package lastfm.grishman.com.lastfmapp.ui.albumDetails

import androidx.lifecycle.LiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import lastfm.grishman.com.lastfmapp.db.AlbumDao
import lastfm.grishman.com.lastfmapp.extensions.addTo
import lastfm.grishman.com.lastfmapp.extensions.failed
import lastfm.grishman.com.lastfmapp.extensions.loading
import lastfm.grishman.com.lastfmapp.extensions.success
import lastfm.grishman.com.lastfmapp.model.album.AlbumInfoResponse
import lastfm.grishman.com.lastfmapp.model.album.DetailedAlbum
import lastfm.grishman.com.lastfmapp.network.LastFmService
import lastfm.grishman.com.lastfmapp.network.Outcome
import lastfm.grishman.com.lastfmapp.vo.ViewAlbum

class DetailsRepository(private val api: LastFmService, private val albumDao: AlbumDao) {

    private val disposable: CompositeDisposable = CompositeDisposable()

    val albumsResult: PublishSubject<Outcome<DetailedAlbum>> =
            PublishSubject.create<Outcome<DetailedAlbum>>()

    fun getAlbumInfo(artist: String, album: String, mbid: String) {
        albumsResult.loading(true)

        api.getAlbumInfo(artist = artist, album = album, mbid = mbid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result: AlbumInfoResponse ->
                            albumsResult.success(result.album)
                            //saveAlbum(result.album)
                        },
                        { error: Throwable -> handleError(error) }
                ).addTo(disposable)

    }

    fun loadFromDb(id: Long, name: String): LiveData<ViewAlbum> {
        return albumDao.getAlbum(id, name = name)
    }

//    fun saveAlbum(album: DetailedAlbum) {
//        Completable.fromAction {
//            detailsDao.saveAlbum(album)
//        }
//                .subscribeOn(Schedulers.io())
//                .subscribe()
//    }

    fun handleError(error: Throwable) {
        albumsResult.failed(error)
    }
}
