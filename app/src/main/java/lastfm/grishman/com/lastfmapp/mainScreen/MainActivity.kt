package lastfm.grishman.com.lastfmapp.mainScreen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import lastfm.grishman.com.lastfmapp.R
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    // Lazy Inject ViewModel
    private val viewModel: MainScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getGreating()
    }
}
