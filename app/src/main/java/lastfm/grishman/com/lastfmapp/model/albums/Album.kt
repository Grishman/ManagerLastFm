package lastfm.grishman.com.lastfmapp.model.albums

import android.arch.persistence.room.*
import com.google.gson.annotations.SerializedName
import lastfm.grishman.com.lastfmapp.model.Artist
import lastfm.grishman.com.lastfmapp.model.Image

/*
Copyright (c) 2018 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */

@Entity(tableName = "albums_table",
        indices = [(Index("name"))])
data class Album(

        @PrimaryKey(autoGenerate = true)
        var id: Long ,

        @SerializedName("name")
//        @PrimaryKey
        @ColumnInfo(name = "name")
        public var name: String? = "",

        @SerializedName("playcount")
        @ColumnInfo(name = "playcount")
        var playCount: Int = 0,

        @field:SerializedName("url")
        @ColumnInfo(name = "url")
        var url: String = "",

        @SerializedName("artist")
        @Ignore
        @ColumnInfo(name = "artist")
        var artist: Artist,

        @SerializedName("image")
        @Ignore
        @ColumnInfo(name = "images")
        var image: List<Image>
) {
    //    constructor() : this()
//    constructor() : this(0,
//            "",
//            0,
//            "",
//            Any() as Artist,
//            emptyList()
//    )
    constructor() : this(id = 0, url = "", name = "", playCount = 0, artist = Artist("",0,"",""), image = emptyList())
}