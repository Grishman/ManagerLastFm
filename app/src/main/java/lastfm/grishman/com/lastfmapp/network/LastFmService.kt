package lastfm.grishman.com.lastfmapp.network

import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * API interface to consume LastFM API's
 */
interface LastFmService {

    //Search artist by name
    @POST("?method=artist.search")
    fun searchArtist(
            @Query("artist") artist: String,
            @Query("format") format: String = "json"
    ): Flowable<ResponseBody>

    //Get Artist's Top Albums
    @GET("?method=artist.gettopalbums")
    fun getTopAlbums(
            @Query("artist") artist: String,
            @Query("mbid") mbid: String,
            @Query("limit") limit: Int = 10,
            @Query("format") format: String = "json"
    ): Flowable<ResponseBody>

    //Get Album Info
    @GET("?method=album.getinfo")
    fun getAlbumInfo(
            @Query("mbid") mbid: String,
            @Query("format") format: String = "json"
    ): Flowable<ResponseBody>
}