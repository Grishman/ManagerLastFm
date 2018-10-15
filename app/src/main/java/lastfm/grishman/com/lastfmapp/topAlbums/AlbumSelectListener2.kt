package lastfm.grishman.com.lastfmapp.topAlbums

import lastfm.grishman.com.lastfmapp.vo.ViewAlbum

/**
 * Callback from recycler view
 */
interface AlbumSelectListener2 {
    fun onAlbumSelected(album: ViewAlbum)
    fun onAlbumSaveAction(album: ViewAlbum)
}