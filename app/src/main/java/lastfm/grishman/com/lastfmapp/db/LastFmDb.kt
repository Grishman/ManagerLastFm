package lastfm.grishman.com.lastfmapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import lastfm.grishman.com.lastfmapp.model.albums.Album

/**
 * App database.
 */
@Database(entities = [Album::class], version = 1, exportSchema = false)
abstract class LastFmDb : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
}