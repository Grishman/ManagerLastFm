package lastfm.grishman.com.lastfmapp.model.albums

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lastfm.grishman.com.lastfmapp.model.Artist
import lastfm.grishman.com.lastfmapp.model.Image


/**
 * Created by Bohdan Bezpartochnyi on 15.10.18.
 */
class Testalbu {
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("playcount")
    @Expose
    var playcount: Int? = null
    @SerializedName("mbid")
    @Expose
    var mbid: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null
    @SerializedName("artist")
    @Expose
    var artist: Artist? = null
    @SerializedName("image")
    @Expose
    var image: List<Image> = ArrayList()


}