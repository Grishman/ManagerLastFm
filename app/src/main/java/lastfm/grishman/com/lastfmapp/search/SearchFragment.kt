package lastfm.grishman.com.lastfmapp.search

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView.OnQueryTextListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.search_fragment.*
import lastfm.grishman.com.lastfmapp.R
import lastfm.grishman.com.lastfmapp.model.SearchResult
import lastfm.grishman.com.lastfmapp.network.Outcome
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.io.IOException

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val searchModel: SearchViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(false)
        initiateDataListener()
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_view.isSubmitButtonEnabled = true
        search_view.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(str: String?): Boolean {
                str?.let { searchModel.search(it) }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean = false
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
        //searchModel.search("kiki")
    }

    private fun initiateDataListener() {
        //Observe the outcome and update state of the screen  accordingly
        searchModel.searchOutcome.observe(this, Observer<Outcome<SearchResult>> { outcome ->
            Timber.d("initiateDataListener: " + outcome.toString())
            when (outcome) {

                is Outcome.Progress -> {
                    searchModel.isRefreshing.set(outcome.loading)
                    progress.visibility = View.VISIBLE
                }

                is Outcome.Success -> {
                    Timber.d("initiateDataListener: Successfully loaded data")
                    //adapter.setData(outcome.data)
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
