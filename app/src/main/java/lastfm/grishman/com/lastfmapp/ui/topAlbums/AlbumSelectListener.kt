package lastfm.grishman.com.lastfmapp.ui.topAlbums

import lastfm.grishman.com.lastfmapp.model.albums.Album

/**
 * Callback from recycler view
 */
interface AlbumSelectListener {
    fun onAlbumSelected(album: Album)
    fun onAlbumSaveAction(album: Album)
}