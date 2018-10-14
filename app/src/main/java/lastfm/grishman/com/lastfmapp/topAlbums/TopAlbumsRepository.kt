package lastfm.grishman.com.lastfmapp.topAlbums

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import lastfm.grishman.com.lastfmapp.db.AlbumDao
import lastfm.grishman.com.lastfmapp.extensions.addTo
import lastfm.grishman.com.lastfmapp.extensions.failed
import lastfm.grishman.com.lastfmapp.extensions.loading
import lastfm.grishman.com.lastfmapp.extensions.success
import lastfm.grishman.com.lastfmapp.model.albums.Album
import lastfm.grishman.com.lastfmapp.model.albums.TopAlbumsResponse
import lastfm.grishman.com.lastfmapp.network.LastFmService
import lastfm.grishman.com.lastfmapp.network.Outcome

class TopAlbumsRepository(private val api: LastFmService, private val albumDao: AlbumDao) {

    private val disposable: CompositeDisposable = CompositeDisposable()

    val albumsResult: PublishSubject<Outcome<List<Album>>> =
            PublishSubject.create<Outcome<List<Album>>>()

    fun getTopAlbums(artist: String, mbid: String) {
        albumsResult.loading(true)

        api.getTopAlbums(artist = artist, mbid = mbid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result: TopAlbumsResponse -> albumsResult.success(result.topAlbums.album) },
                        { error: Throwable -> handleError(error) }
                ).addTo(disposable)


    }

    fun saveAlbumTest(album: Album) {
        Completable.fromAction {
            albumDao.saveAlbum(album)
        }
                .subscribeOn(Schedulers.io())
                .subscribe()
        //albumDao.saveAlbum(album)
    }

    fun handleError(error: Throwable) {
        albumsResult.failed(error)
    }
}
