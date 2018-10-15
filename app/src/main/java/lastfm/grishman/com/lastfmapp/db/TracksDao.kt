package lastfm.grishman.com.lastfmapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import lastfm.grishman.com.lastfmapp.model.album.Track

/**
 * DAO for tracks.
 */
@Dao
interface TracksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAll(albums: List<Track>)

    @Delete
    fun delete(album: Track)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTrack(album: Track)

    @Query("SELECT DISTINCT * FROM tracks_table")
    fun getAll(): LiveData<List<Track>>
}