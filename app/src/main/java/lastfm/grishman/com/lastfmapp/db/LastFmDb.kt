package lastfm.grishman.com.lastfmapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import lastfm.grishman.com.lastfmapp.model.album.DetailedAlbum
import lastfm.grishman.com.lastfmapp.vo.ViewAlbum
import lastfm.grishman.com.lastfmapp.vo.ViewTracks

/**
 * App database.
 */
@Database(entities = [ViewAlbum::class, ViewTracks::class, DetailedAlbum::class], version = 1, exportSchema = false)
abstract class LastFmDb : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
    abstract fun tracksDao(): TracksDao
    abstract fun detailsDao(): DetailAlbumDao
    abstract fun testDao(): AlbumWithTracksDao
}