package lastfm.grishman.com.lastfmapp.mainScreen


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_albums.*

import lastfm.grishman.com.lastfmapp.R
import lastfm.grishman.com.lastfmapp.model.albums.Album
import lastfm.grishman.com.lastfmapp.topAlbums.AlbumAdapter
import lastfm.grishman.com.lastfmapp.topAlbums.AlbumSelectListener
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class AlbumsFragment : Fragment(), AlbumSelectListener {
    override fun onAlbumSelected(album: Album) {
        //fixme open details
        viewModel.removeAlbum(album)
    }

    override fun onAlbumSaveAction(album: Album) {
        viewModel.removeAlbum(album)
    }

    private lateinit var navController: NavController

    // Lazy Inject ViewModel
    private val viewModel: MainScreenViewModel by viewModel()

    private val adapter: AlbumAdapter = AlbumAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_albums, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_albums.adapter = adapter
        navController = Navigation.findNavController(view)
        viewModel.getAlbums().observe(this, Observer<List<Album>> {
            it?.let {
                Timber.d("size of items is" + it.size)
                textDb.text = "lol"
                adapter.swapItems(it)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search_action -> {
                val options = NavOptions.Builder()
                        .setEnterAnim(R.anim.slide_in_right)
                        .setExitAnim(R.anim.slide_out_left)
                        .setPopEnterAnim(R.anim.slide_in_left)
                        .setPopExitAnim(R.anim.slide_out_right)
                        .build()

//                view.findViewById<Button>(R.id.navigate_dest_bt)?.setOnClickListener {
                //Navigation.createNavigateOnClickListener(R.id.settings_fragment2, null)
//                NavigationUI.findNavController(this, R.id.my_nav_host_fragment)
                navController.navigate(R.id.searchFragment, null, options)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
