package lastfm.grishman.com.lastfmapp.topAlbums

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import lastfm.grishman.com.lastfmapp.db.AlbumDao
import lastfm.grishman.com.lastfmapp.db.TracksDao
import lastfm.grishman.com.lastfmapp.extensions.addTo
import lastfm.grishman.com.lastfmapp.extensions.failed
import lastfm.grishman.com.lastfmapp.extensions.loading
import lastfm.grishman.com.lastfmapp.extensions.success
import lastfm.grishman.com.lastfmapp.model.album.DetailedAlbum
import lastfm.grishman.com.lastfmapp.model.album.Track
import lastfm.grishman.com.lastfmapp.model.albums.Album
import lastfm.grishman.com.lastfmapp.network.LastFmService
import lastfm.grishman.com.lastfmapp.network.Outcome
import lastfm.grishman.com.lastfmapp.vo.ViewAlbum
import lastfm.grishman.com.lastfmapp.vo.ViewTracks

class TopAlbumsRepository(private val api: LastFmService, private val albumDao: AlbumDao, private val trackDao: TracksDao) {

    private val disposable: CompositeDisposable = CompositeDisposable()

    val albumsResult: PublishSubject<Outcome<List<Album>>> =
            PublishSubject.create<Outcome<List<Album>>>()

    val albumsResultTest: PublishSubject<Outcome<List<ViewAlbum>>> =
            PublishSubject.create<Outcome<List<ViewAlbum>>>()

    fun getTopAlbums(artist: String, mbid: String) {
        //albumsResult.loading(true)
        albumsResultTest.loading(true)

//        Single.zip(
//                api.getTopAlbums(artist = artist, mbid = mbid),
//                api.getAlbumInfo(artist, album = "Islah (Deluxe)", mbid = mbid),
//                zipAlbumAndDetails()
//        )

        api.getTopAlbums(artist = artist, mbid = mbid)
                //.map { mapApiToViewModel(it) }
                .map {
                    var resultList: MutableList<ViewAlbum> = mutableListOf<ViewAlbum>()
                    it.topAlbums.album.forEachIndexed { index, album -> resultList?.add(index, album.convertAlbum(album)) }
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

//    private fun mapApiToViewModel(response: TopAlbumsResponse) = Function<TopAlbumsResponse, List<ViewAlbum>> {
//
//        var resultList: MutableList<ViewAlbum>? = mutableListOf<ViewAlbum>()
//        it.topAlbums.album.forEachIndexed { index, album -> resultList?.add(index, album.convertAlbum(album)) }
////        resultList = it.topAlbums.album
//        return@Function resultList
//
//    }

    /**
     * Using .zip method to create our end data.
     */
//    private fun zipAlbumAndDetails() =
//            BiFunction<TopAlbumsResponse, AlbumInfoResponse, List<ViewAlbum>> { album, detailedAlbum ->
//                //form ViewAlbum Models with tracks
//                var resultList: MutableList<ViewAlbum> = mutableListOf<ViewAlbum>()
//                album.topAlbums.album.forEachIndexed { index, album -> resultList?.add(index, album.convertAlbum(album, detailedAlbum = detailedAlbum.album)) }
////        resultList = it.topAlbums.album
//                return@BiFunction resultList
//            }

    fun saveAlbumTest(album: ViewAlbum) {
        api.getAlbumInfo(album = album.name, artist = album.artist, mbid = album.mbid ?: "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { res -> saveToDB(res.album) },
                        { error: Throwable -> handleError(error) }
//                        { result: TopAlbumsResponse -> albumsResult.success(result.topAlbums.album) },
//                        { error: Throwable -> handleError(error) }
                ).addTo(disposable)

        //albumDao.saveAlbum(album)
    }

    private fun saveToDB(album: DetailedAlbum) {
        Completable.fromAction {
            albumDao.saveAlbum(album.convertAlbum2(album))
            trackDao.upsertAll(albums = toViewTracks(album.tracks?.track))
        }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    private fun toViewTracks(track: List<Track>?): List<ViewTracks> {
        var resultList: MutableList<ViewTracks> = mutableListOf<ViewTracks>()
        track?.forEachIndexed { index, album -> resultList.add(index, album.convertTrack()) }
//        resultList = it.topAlbums.album
        return resultList
    }

    fun handleError(error: Throwable) {
        albumsResult.failed(error)
    }
}
