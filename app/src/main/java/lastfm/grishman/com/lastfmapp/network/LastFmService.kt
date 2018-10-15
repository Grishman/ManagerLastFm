package lastfm.grishman.com.lastfmapp.network

import io.reactivex.Single
import lastfm.grishman.com.lastfmapp.model.SearchResult
import lastfm.grishman.com.lastfmapp.model.album.AlbumInfoResponse
import lastfm.grishman.com.lastfmapp.model.albums.TopAlbumsResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API interface to consume LastFM API's
 */
interface LastFmService {

    //Search artist by name
    @GET("?method=artist.search")
    fun searchArtist(
            @Query("artist") artist: String
    ): Single<SearchResult>

    //Get Artist's Top Albums
    @GET("?method=artist.gettopalbums")
    fun getTopAlbums(
            @Query("artist") artist: String,
            @Query("mbid") mbid: String,
            @Query("limit") limit: Int = 10
    ): Single<TopAlbumsResponse>

    //Get Album details info
    @GET("?method=album.getinfo")
    fun getAlbumInfo(
            @Query("artist") artist: String,
            @Query("album") album: String,
            @Query("mbid") mbid: String
    ): Single<AlbumInfoResponse>
}