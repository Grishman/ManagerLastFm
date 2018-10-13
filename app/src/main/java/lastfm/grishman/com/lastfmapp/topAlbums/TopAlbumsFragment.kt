package lastfm.grishman.com.lastfmapp.topAlbums

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.top_albums_fragment.*
import lastfm.grishman.com.lastfmapp.R
import lastfm.grishman.com.lastfmapp.model.Artist
import lastfm.grishman.com.lastfmapp.model.albums.Album
import lastfm.grishman.com.lastfmapp.network.Outcome
import lastfm.grishman.com.lastfmapp.utils.SpacesItemDecoration
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.io.IOException


class TopAlbumsFragment : Fragment(), AlbumSelectListener {
    override fun onAlbumSelected(album: Album) {
    }

    private var artist: Artist? = null


    companion object {
        @JvmStatic
        fun newInstance() = TopAlbumsFragment()
    }

    private val viewModel: TopAlbumsViewModel by viewModel()

    private lateinit var gridLayoutManager: GridLayoutManager

    private val adapter: AlbumAdapter = AlbumAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gridLayoutManager = GridLayoutManager(context, 2)
        artist = arguments?.getParcelable<Artist>("artist")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(false)
        initiateDataListener()
        return inflater.inflate(R.layout.top_albums_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //hello.text = artist.toString()
        //init Recycler for albums
        recycler_albums.layoutManager = gridLayoutManager
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        recycler_albums.addItemDecoration(SpacesItemDecoration(spacingInPixels))
        recycler_albums.adapter = adapter
        artist?.let {
            viewModel.search(it.name, it.mbid)
        }

    }

    private fun initiateDataListener() {
        //Observe the outcome and update state of the screen  accordingly
        viewModel.searchOutcome.observe(this, Observer<Outcome<List<Album>>> { outcome ->
            Timber.d("initiateDataListener: ${outcome.toString()}")
            when (outcome) {

                is Outcome.Progress -> {
                    viewModel.isRefreshing.set(outcome.loading)
                    progress.visibility = View.VISIBLE
                }

                is Outcome.Success -> {
                    Timber.d("initiateDataListener: Successfully loaded data")
                    adapter.swapItems(outcome.data)
                    progress.visibility = View.INVISIBLE
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
