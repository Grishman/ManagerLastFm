package lastfm.grishman.com.lastfmapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import lastfm.grishman.com.lastfmapp.vo.AlbumWithTracks

/**
 * Dao
 */
@Dao
interface AlbumWithTracksDao {

    @Query("SELECT DISTINCT * FROM albums_table")
    fun getAll(): LiveData<List<AlbumWithTracks>>
}