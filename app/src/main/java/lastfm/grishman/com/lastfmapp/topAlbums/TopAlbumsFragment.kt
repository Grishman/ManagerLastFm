package lastfm.grishman.com.lastfmapp.topAlbums

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.top_albums_fragment.*
import lastfm.grishman.com.lastfmapp.R
import lastfm.grishman.com.lastfmapp.model.Artist


class TopAlbumsFragment : Fragment() {
    private var artist: Artist? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hello.text = artist.toString()
    }

    companion object {
        @JvmStatic
        fun newInstance() = TopAlbumsFragment()
    }

    private lateinit var viewModel: TopAlbumsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        artist = arguments?.getParcelable<Artist>("artist")

        return inflater.inflate(R.layout.top_albums_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TopAlbumsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
