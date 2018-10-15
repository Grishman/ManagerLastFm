package lastfm.grishman.com.lastfmapp.model.albums

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Ignore
import com.google.gson.annotations.SerializedName
import lastfm.grishman.com.lastfmapp.model.Artist
import lastfm.grishman.com.lastfmapp.model.Image
import lastfm.grishman.com.lastfmapp.vo.ViewAlbum

/*
Copyright (c) 2018 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class Album(
//        @PrimaryKey(autoGenerate = true)
        var id: Long
) {
    @Ignore constructor() : this(0)

    @SerializedName("name")
//        @PrimaryKey
    @ColumnInfo(name = "name")
    var name: String = ""

    @SerializedName("playcount")
    @ColumnInfo(name = "playcount")
    var playCount: Int = 0

    @field:SerializedName("url")
    @ColumnInfo(name = "url")
    var url: String = ""

    @field:SerializedName("mbid")
    @ColumnInfo(name = "mbid")
    var mbid: String = ""

    @SerializedName("artist")
    @Ignore
    @ColumnInfo(name = "artist")
    @Embedded
    var artist: Artist = Artist("", 0, "", "")

    @SerializedName("image")
    @Ignore
    @ColumnInfo(name = "images")
    @Embedded
    val image: List<Image> = emptyList()
    //    constructor() : this()
//    constructor() : this(0,
//            "",
//            0,
//            "",
//            Any() as Artist,
//            emptyList()
//    )

    @Ignore
     fun convertAlbum(album: Album): ViewAlbum {
        return ViewAlbum(
                name = album.name,
                mbid = album.mbid,
                artist = album.artist.name,
                imageUri = album.image[3].text
        )
    }
    //constructor() : this(id = 0, url = "", name = "", playCount = 0, artist = Artist("",0,"",""), image = emptyList())
}