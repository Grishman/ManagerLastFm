package lastfm.grishman.com.lastfmapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import lastfm.grishman.com.lastfmapp.model.album.DetailedAlbum

/**
 * DAO for tracks.
 */
@Dao
interface DetailAlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAll(albums: List<DetailedAlbum>)

    @Delete
    fun delete(album: DetailedAlbum)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAlbum(album: DetailedAlbum)

    @Query("SELECT DISTINCT * FROM detailed_album_table")
    fun getAll(): LiveData<List<DetailedAlbum>>
}