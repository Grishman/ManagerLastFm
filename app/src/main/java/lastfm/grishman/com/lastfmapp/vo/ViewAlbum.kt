package lastfm.grishman.com.lastfmapp.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import lastfm.grishman.com.lastfmapp.model.albums.Album

/**
 * View Object to save to DB.
 */
@Entity(tableName = "albums_table",
        primaryKeys = ["id", "name"],
        indices = [(Index("name"))])
data class ViewAlbum(val id: Long = 0L,
                     val name: String,
                     val mbid: String?,
                     val artist: String,
                     @ColumnInfo(name = "image") val imageUri: String
//                     @Relation(parentColumn = "track_id", entityColumn = "id")
        //@Embedded

) {
    //    @Ignore
    //@Relation(parentColumn = "id", entityColumn = "track_id")
    @Ignore
    var tracks: MutableList<ViewTracks> = mutableListOf()

    @Ignore
    open fun convertAlbum(album: Album): ViewAlbum {
        return ViewAlbum(
                name = album.name,
                mbid = album.mbid,
                artist = album.artist.name,
                imageUri = album.image[3].text
        )
    }
}