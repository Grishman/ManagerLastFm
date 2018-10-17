package lastfm.grishman.com.lastfmapp.vo

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import kotlinx.android.parcel.Parcelize

/**
 * View Object to save to DB.
 */
@Entity(tableName = "albums_table",
        primaryKeys = ["id", "name"],
        indices = [(Index("name"))])
@Parcelize
//FixMe id is always 0, when we define as primaryKeys = ["id", "name"]
data class ViewAlbum(val id: Long = 0L,
                     val name: String,
                     val mbid: String? = "",
                     val artist: String,
                     @ColumnInfo(name = "image") val imageUri: String) : Parcelable {
//    @Ignore
//    fun convertAlbum(album: Album): ViewAlbum {
//        return ViewAlbum(
//                name = album.name,
//                mbid = album.mbid,
//                artist = album.artist.name,
//                imageUri = album.image[3].text
//        )
//    }
}