package lastfm.grishman.com.lastfmapp.network

import io.reactivex.Flowable
import okhttp3.Response
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * API interface to consume LastFM API's
 */
interface LastFmService {

    @POST("/?method=artist.search")
    fun searchArtist(
            @Query("artist") artist: String,
            @Query("format") format: String = "json"
    ): Flowable<Response>
}