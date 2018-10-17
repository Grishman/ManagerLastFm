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
import kotlinx.android.parcel.IgnoredOnParcel
import lastfm.grishman.com.lastfmapp.model.Image

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

        @SerializedName("listeners") val listeners: Int,
        @SerializedName("playcount") val playcount: Int
        //@Relation(parentColumn = "album_id", entityColumn = "track_id")

//		@SerializedName("tags") val tags : Tags,
//		@SerializedName("wiki") val wiki : Wiki
) {
    @Ignore
    @SerializedName("image")
    val image: List<Image> = emptyList()

    // does not show up in the response but set in post processing.
    @IgnoredOnParcel
    @Ignore
    //fixme move to utils class
    var avatar: String = ""
        get() = image.find { it.size == "large" }?.text ?: ""

    @Ignore
    @SerializedName("tracks")
    val tracks: Tracks? = null

    @Ignore
    constructor(name: String, artist: String, avatar: String) : this(0, name = name, artist = artist, url = "", listeners = 0, playcount = 0) {
        this.avatar = avatar
    }
}