package lastfm.grishman.com.lastfmapp.network

import okhttp3.Interceptor
import okhttp3.Response


/**
 * Intercepter to put API key to every request.
 */
class AddAPIKeyInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val url = request().url().newBuilder()
                .addQueryParameter("api_key", apiKey)
                .build()
        proceed(request().newBuilder().url(url).build())
    }
}