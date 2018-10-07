package lastfm.grishman.com.lastfmapp.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


/**
 * Intercepter to put API key to every request.
 */
class AddAPIKeyInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        val original: Request? = chain?.request()
        val originalHttpUrl = original?.url()

        val url = originalHttpUrl?.newBuilder()
                ?.addQueryParameter("api_key", apiKey)
                ?.build()

        // Request customization: add request headers
        val requestBuilder = original?.newBuilder()
                ?.url(url)

        val request = requestBuilder?.build()
        return chain?.proceed(request)!!
        //fixme do something with nullability
    }
}