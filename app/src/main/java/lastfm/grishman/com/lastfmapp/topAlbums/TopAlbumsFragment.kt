package lastfm.grishman.com.lastfmapp.topAlbums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.top_albums_fragment.*
import lastfm.grishman.com.lastfmapp.R
import lastfm.grishman.com.lastfmapp.model.Artist
import lastfm.grishman.com.lastfmapp.network.Outcome
import lastfm.grishman.com.lastfmapp.utils.SpacesItemDecoration
import lastfm.grishman.com.lastfmapp.vo.ViewAlbum
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.io.IOException


class TopAlbumsFragment : Fragment(), AlbumSelectListener2 {
    override fun onAlbumSaveAction(album: ViewAlbum) {
        //Save to db
        viewModel.saveAlbum(album)
        //navController().navigate(R.id.action_backToMain)

    }

    override fun onAlbumSelected(album: ViewAlbum) {
        navController().navigate(R.id.action_to_albumDetailFragment)
    }

    private fun navController() = findNavController()
    private var artist: Artist? = null


    companion object {
        @JvmStatic
        fun newInstance() = TopAlbumsFragment()
    }

    private val viewModel: TopAlbumsViewModel by viewModel()

    private lateinit var gridLayoutManager: GridLayoutManager

    private val adapter: AlbumAdapter = AlbumAdapter(this)
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
        gridLayoutManager = GridLayoutManager(context, 2)
        artist = arguments?.getParcelable<Artist>("artist")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.top_albums_fragment, container, false)
        initiateDataListener()
//        recyclerView = view.findViewById(R.id.recycler_albums)
//        if (!recyclerView.layoutManager?.isAttachedToWindow!!) {
//        if (recyclerView.layoutManager==null && !recyclerView.isAttachedToWindow)
//            recyclerView.layoutManager = gridLayoutManager
////        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //hello.text = artist.toString()
        //init Recycler for albums
        //recycler_albums.layoutManager = gridLayoutManager
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        recycler_albums.addItemDecoration(SpacesItemDecoration(spacingInPixels))
        recycler_albums.adapter = adapter
        artist?.let {
            viewModel.search(it.name, it.mbid)
        }

    }

    private fun initiateDataListener() {
        //Observe the outcome and update state of the screen  accordingly
        viewModel.searchOutcome2.observe(this, Observer<Outcome<List<ViewAlbum>>> { outcome ->
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
