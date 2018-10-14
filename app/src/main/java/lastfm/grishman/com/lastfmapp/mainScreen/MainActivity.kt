package lastfm.grishman.com.lastfmapp.mainScreen

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import lastfm.grishman.com.lastfmapp.R
import lastfm.grishman.com.lastfmapp.model.albums.Album
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    // Lazy Inject ViewModel
    private val viewModel: MainScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val host: NavHostFragment = supportFragmentManager
                .findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment? ?: return


        // Set up Action Bar
        val navController = host.navController
        setupActionBar(navController)

        val options = NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build()

        viewModel.getAlbums().observe(this, Observer<List<Album>>{
            it?.let { Timber.d("size of items is"+ it[0]) }
        })
//        findViewById<TextView>(R.id.some_text)?.setOnClickListener {
//            navController.navigate(R.id.search_action, null, options)
//        }
    }

    override fun onStart() {
        super.onStart()
//        viewModel.getGreating()

    }

    private fun setupActionBar(navController: NavController) {
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.main_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Have the NavHelper look for an action or destination matching the menu
        // item id and navigate there if found.
        // Otherwise, bubble up to the parent.
//        return NavigationUI.onNavDestinationSelected(item,
//                Navigation.findNavController(this, R.id.main_nav_host_fragment))
//                ||
                return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.main_nav_host_fragment).navigateUp()

}
