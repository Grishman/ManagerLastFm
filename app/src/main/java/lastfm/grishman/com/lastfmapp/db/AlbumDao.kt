package lastfm.grishman.com.lastfmapp.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import lastfm.grishman.com.lastfmapp.model.albums.Album

/**
 * DAO for albums.
 */
@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAll(albums: List<Album>)

    @Delete
    fun delete(album: Album)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAlbum(album: Album)

    @Query("SELECT * FROM albums_table")
    fun getAll(): LiveData<List<Album>>
}