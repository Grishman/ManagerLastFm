/* 
Copyright (c) 2018 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */
package lastfm.grishman.com.lastfmapp.model.album

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lastfm.grishman.com.lastfmapp.model.Image
import lastfm.grishman.com.lastfmapp.vo.ViewAlbum
import lastfm.grishman.com.lastfmapp.vo.ViewTracks

@Entity(tableName = "detailed_album_table",
        primaryKeys = ["album_id", "name", "artist"],
        indices = [(Index("album_id", "name"))])
data class DetailedAlbum(
        @ColumnInfo(name = "album_id")
        @Expose
        val albumId: Long,
        @SerializedName("name") val name: String,
        @SerializedName("artist") val artist: String,
        @SerializedName("url") val url: String,
        @field:SerializedName("mbid")
        @ColumnInfo(name = "mbid")
        val mbid: String?,
        @SerializedName("listeners") val listeners: Int,
        @SerializedName("playcount") val playcount: Int
        //@Relation(parentColumn = "album_id", entityColumn = "track_id")

//		@SerializedName("tags") val tags : Tags,
//		@SerializedName("wiki") val wiki : Wiki
) {
    @Ignore
    @SerializedName("image")
    val image: List<Image> = emptyList()

    @Ignore
    @SerializedName("tracks")
    val tracks: Tracks? = null

    @Ignore
    fun convertAlbum2(detailedAlbum: DetailedAlbum): ViewAlbum {
        var model = ViewAlbum(
                name = detailedAlbum.name,
                mbid = detailedAlbum.mbid,
                artist = detailedAlbum.artist,
                imageUri = detailedAlbum.image[3].text

        )
        model.tracks = toViewTracks(detailedAlbum.tracks?.track).toMutableList()
        return model
    }

    private fun toViewTracks(track: List<Track>?): List<ViewTracks> {
        var resultList: MutableList<ViewTracks> = mutableListOf<ViewTracks>()
        track?.forEachIndexed { index, album -> resultList.add(index, album.convertTrack()) }
//        resultList = it.topAlbums.album
        return resultList
    }
}