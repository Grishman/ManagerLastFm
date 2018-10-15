package lastfm.grishman.com.lastfmapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import lastfm.grishman.com.lastfmapp.vo.ViewAlbum

/**
 * DAO for albums.
 */
@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAll(albums: List<ViewAlbum>)

    @Delete
    fun delete(album: ViewAlbum)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAlbum(album: ViewAlbum)

    @Query("SELECT DISTINCT * FROM albums_table")
    fun getAll(): LiveData<List<ViewAlbum>>
}