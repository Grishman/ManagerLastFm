package lastfm.grishman.com.lastfmapp.search

import android.arch.lifecycle.ViewModel

class SearchViewModel(private val repo: SearchRepository) : ViewModel() {
    // TODO: Implement the ViewModel

    fun search(artistToSearch: String) {
        repo.searchArtist(artistToSearch)
    }
}
