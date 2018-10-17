package lastfm.grishman.com.lastfmapp.mainScreen

import androidx.lifecycle.LiveData
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import lastfm.grishman.com.lastfmapp.db.AlbumDao
import lastfm.grishman.com.lastfmapp.network.LastFmService
import lastfm.grishman.com.lastfmapp.vo.ViewAlbum

class AlbumsRepository(private val api: LastFmService, private val albumDao: AlbumDao) {

    fun loadFromDb(): LiveData<List<ViewAlbum>> {
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
