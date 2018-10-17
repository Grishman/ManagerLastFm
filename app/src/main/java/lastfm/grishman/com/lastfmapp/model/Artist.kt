package lastfm.grishman.com.lastfmapp.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

/*
Copyright (c) 2018 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */

@SuppressLint("ParcelCreator")
@Parcelize
data class Artist(
        @SerializedName("name") val name: String,
        @SerializedName("listeners") val listeners: Int,
        @SerializedName("mbid") val mbid: String,
        @SerializedName("url") val url: String,
        @SerializedName("streamable") val streamable: Int,
        @SerializedName("image") val image: @RawValue List<Image> //fixme images
) : Parcelable {
    constructor(name: String, listeners: Int, mbid: String, url: String) : this(name, listeners, mbid, url, 0, emptyList())

    // does not show up in the response but set in post processing.
    @IgnoredOnParcel
    //fixme move to utils class
    var avatar: String = ""
        get() = image.find { it.size == "large" }?.text ?: ""
}

