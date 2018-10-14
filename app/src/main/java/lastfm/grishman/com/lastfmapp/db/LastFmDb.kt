package lastfm.grishman.com.lastfmapp.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import lastfm.grishman.com.lastfmapp.model.albums.Album

/**
 * App database.
 */
@Database(entities = [Album::class], version = 1, exportSchema = false)
abstract class LastFmDb : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
}