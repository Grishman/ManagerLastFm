package lastfm.grishman.com.lastfmapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import lastfm.grishman.com.lastfmapp.vo.ViewTracks

/**
 * DAO for tracks.
 */
@Dao
interface TracksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAll(albums: List<ViewTracks>)

    @Delete
    fun delete(album: ViewTracks)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTrack(album: ViewTracks)

    @Query("SELECT DISTINCT * FROM tracks_table")
    fun getAll(): LiveData<List<ViewTracks>>
}