package lastfm.grishman.com.lastfmapp.albumDetails

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.album_detail_fragment.*
import lastfm.grishman.com.lastfmapp.R
import lastfm.grishman.com.lastfmapp.model.album.DetailedAlbum
import lastfm.grishman.com.lastfmapp.network.Outcome
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.io.IOException

class AlbumDetailFragment : Fragment() {

    companion object {
        fun newInstance() = AlbumDetailFragment()
    }


    private val viewModel: AlbumDetailViewModel by viewModel()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        initiateDataListener()
        return inflater.inflate(R.layout.album_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        artist?.let {
        viewModel.search(
                artistToSearch = "Kevin Gates",
                albumName = "Islah (Deluxe)",
                mbid = ""
        )
//        }

    }

    private fun initiateDataListener() {
        //Observe the outcome and update state of the screen  accordingly
        viewModel.searchOutcome.observe(this, Observer<Outcome<DetailedAlbum>> { outcome ->
            Timber.d("initiateDataListener: ${outcome.toString()}")
            when (outcome) {

                is Outcome.Progress -> {
                    viewModel.isRefreshing.set(outcome.loading)
                    progress.visibility = View.VISIBLE
                }

                is Outcome.Success -> {
                    Timber.d("initiateDataListener: Successfully loaded data${outcome.data.tracks}")
                    progress.visibility = View.INVISIBLE
                    testinfo.text = outcome.data.toString()
                }

                is Outcome.Failure -> {

                    if (outcome.e is IOException)
                        Toast.makeText(
                                context,
                                "No internet",
                                Toast.LENGTH_LONG
                        ).show()
                    else
                        Toast.makeText(
                                context,
                                "Failed",
                                Toast.LENGTH_LONG
                        ).show()
                }

            }
        })
    }
}
