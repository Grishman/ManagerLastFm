package lastfm.grishman.com.lastfmapp.ui.albumDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.album_detail_fragment.*
import lastfm.grishman.com.lastfmapp.R
import lastfm.grishman.com.lastfmapp.databinding.AlbumDetailFragmentBinding
import lastfm.grishman.com.lastfmapp.model.album.DetailedAlbum
import lastfm.grishman.com.lastfmapp.network.Outcome
import lastfm.grishman.com.lastfmapp.ui.topAlbums.TopAlbumsFragment
import lastfm.grishman.com.lastfmapp.vo.ViewAlbum
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.io.IOException

class AlbumDetailFragment : Fragment() {

    companion object {
        const val FROM_DB: String = "FROM_DB"
    }

    private val detailViewModel: AlbumDetailViewModel by viewModel()
    private lateinit var binding: AlbumDetailFragmentBinding
    private val adapter: TracksAdapter = TracksAdapter()
    private var album: ViewAlbum? = null

    private var isFromDB = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Get params from extras.
        arguments?.let {
            album = it.getParcelable(TopAlbumsFragment.ALBUM_PARAM)
            isFromDB = it.getBoolean(FROM_DB, false)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val dataBinding = DataBindingUtil.inflate<AlbumDetailFragmentBinding>(
                inflater,
                R.layout.album_detail_fragment,
                container,
                false
        )

        initiateDataListener()
        binding = dataBinding
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //todo add network checks
        recycler_tracks.adapter = adapter
        album?.let { it ->
            if (isFromDB) {
                //fetch from DB
                detailViewModel.getDetailedAlbum(it).observe(
                        this,
                        Observer<ViewAlbum> {
                            binding.viewModel = DetailedAlbum(it.name, it.artist, it.imageUri)
                        }
                )
            } else {
                detailViewModel.search(
                        artistToSearch = it.artist,
                        albumName = it.name,
                        mbid = it.mbid
                )
            }
        }

    }

    private fun initiateDataListener() {
        //Observe the outcome and update state of the screen  accordingly
        detailViewModel.searchOutcome.observe(this, Observer<Outcome<DetailedAlbum>> { outcome ->
            Timber.d("initiateDataListener: ${outcome.toString()}")
            when (outcome) {

                is Outcome.Progress -> {
                    detailViewModel.isRefreshing.set(outcome.loading)
                    progress.visibility = View.VISIBLE
                }

                is Outcome.Success -> {
                    Timber.d("initiateDataListener: Successfully loaded data${outcome.data.tracks}")
                    binding.viewModel = outcome.data
                    //fixme
                    outcome.data.tracks?.track?.let { adapter.swapItems(it) }
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
