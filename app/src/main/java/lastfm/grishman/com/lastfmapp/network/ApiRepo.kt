package lastfm.grishman.com.lastfmapp.network

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

class ApiRepo(private val api: LastFmService) {

    val disposable: CompositeDisposable = CompositeDisposable()
    fun getGreat() {
        disposable.add(
                api.getAlbumInfo("023af53b-2a8f-467e-aadc-307b3372ecc2")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { t: ResponseBody? -> Log.d("sk", t?.toString()) },
                                { error: Throwable? -> }
                        )
        )

    }

}
