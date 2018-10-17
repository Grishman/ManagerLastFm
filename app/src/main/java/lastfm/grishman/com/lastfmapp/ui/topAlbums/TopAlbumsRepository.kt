package lastfm.grishman.com.lastfmapp.ui.topAlbums

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
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
import lastfm.grishman.com.lastfmapp.vo.ViewAlbum

class TopAlbumsRepository(private val api: LastFmService, private val albumDao: AlbumDao) {

    private val disposable: CompositeDisposable = CompositeDisposable()

    val albumsResult: PublishSubject<Outcome<List<Album>>> =
            PublishSubject.create<Outcome<List<Album>>>()

    val albumsResultTest: PublishSubject<Outcome<List<ViewAlbum>>> =
            PublishSubject.create<Outcome<List<ViewAlbum>>>()

    fun getTopAlbums(artist: String, mbid: String) {
        //albumsResult.loading(true)
        albumsResultTest.loading(true)

        api.getTopAlbums(artist = artist, mbid = mbid)
                //.map { mapApiToViewModel(it) }
                .map {
                    var resultList: MutableList<ViewAlbum> = mutableListOf<ViewAlbum>()
                    it.topAlbums.album.forEachIndexed { index, album -> resultList.add(index, album.convertAlbum(album)) }
//        resultList = it.topAlbums.album
                    return@map resultList
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { res -> albumsResultTest.success(res) },
                        { error: Throwable -> handleError(error) }
//                        { result: TopAlbumsResponse -> albumsResult.success(result.topAlbums.album) },
//                        { error: Throwable -> handleError(error) }
                ).addTo(disposable)


    }

    private fun mapApiToViewModel(response: TopAlbumsResponse) = Function<TopAlbumsResponse, List<ViewAlbum>> {

        var resultList: MutableList<ViewAlbum>? = mutableListOf<ViewAlbum>()
        it.topAlbums.album.forEachIndexed { index, album -> resultList?.add(index, album.convertAlbum(album)) }
//        resultList = it.topAlbums.album
        return@Function resultList

    }

    fun saveAlbumTest(album: ViewAlbum) {
        Completable.fromAction {
            albumDao.saveAlbum(album)
        }
                .subscribeOn(Schedulers.io())
                .subscribe()
        //albumDao.saveAlbum(album)
    }

    fun handleError(error: Throwable) {
        albumsResultTest.failed(error)
    }
}
