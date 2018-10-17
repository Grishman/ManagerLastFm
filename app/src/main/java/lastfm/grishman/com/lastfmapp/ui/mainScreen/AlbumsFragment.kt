package lastfm.grishman.com.lastfmapp.ui.mainScreen


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_albums.*
import lastfm.grishman.com.lastfmapp.R
import lastfm.grishman.com.lastfmapp.ui.topAlbums.AlbumAdapter
import lastfm.grishman.com.lastfmapp.ui.topAlbums.AlbumSelectListener2
import lastfm.grishman.com.lastfmapp.vo.ViewAlbum
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
class AlbumsFragment : Fragment(), AlbumSelectListener2 {
    override fun onAlbumSelected(album: ViewAlbum) {
        //fixme open details
        //viewModel.removeAlbum(album)
    }

    override fun onAlbumSaveAction(album: ViewAlbum) {
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
        viewModel.getAlbums().observe(this, Observer<List<ViewAlbum>> {
            it?.let {
                Timber.d("size of items is %s", it.size)
                if (it.isNotEmpty()) {
                    no_albums.visibility = View.GONE
                } else {
                    no_albums.visibility = View.VISIBLE
                }
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
                navController.navigate(R.id.searchFragment, null, options)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
