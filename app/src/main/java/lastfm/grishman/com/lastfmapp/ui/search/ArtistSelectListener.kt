package lastfm.grishman.com.lastfmapp.ui.search

import lastfm.grishman.com.lastfmapp.model.Artist

/**
 * Callback from recycler view
 */
interface ArtistSelectListener {
    fun onArtistSelected(artist: Artist)
}