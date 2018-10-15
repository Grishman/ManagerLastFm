package lastfm.grishman.com.lastfmapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import lastfm.grishman.com.lastfmapp.model.album.DetailedAlbum
import lastfm.grishman.com.lastfmapp.model.album.Track
import lastfm.grishman.com.lastfmapp.vo.ViewAlbum

/**
 * App database.
 */
@Database(entities = [ViewAlbum::class, Track::class, DetailedAlbum::class], version = 1, exportSchema = false)
abstract class LastFmDb : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
    abstract fun tracksDao(): TracksDao
    abstract fun detailsDao(): DetailAlbumDao
}