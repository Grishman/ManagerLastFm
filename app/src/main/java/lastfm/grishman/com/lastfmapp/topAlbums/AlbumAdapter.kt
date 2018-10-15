/**
 * Copyright (C)
 */
package lastfm.grishman.com.lastfmapp.topAlbums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import lastfm.grishman.com.lastfmapp.R
import lastfm.grishman.com.lastfmapp.databinding.AlbumItemBinding
import lastfm.grishman.com.lastfmapp.model.albums.Album

/**
 * Adapter for albums.
 */
class AlbumAdapter(val clickListener: AlbumSelectListener) : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    private var albums: List<Album> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val artistItemBinding = DataBindingUtil.inflate<AlbumItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.album_item,
                parent,
                false)
        artistItemBinding.root.setOnClickListener {
            artistItemBinding.viewModel?.let {
                clickListener.onAlbumSelected(it)
            }
        }
        artistItemBinding.actionSaveAlbum.setOnClickListener {
            artistItemBinding.viewModel?.let {
                clickListener.onAlbumSaveAction(it)
            }
        }

        return ViewHolder(artistItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.viewModel = albums[position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = albums.size


    fun swapItems(accountsResponse: List<Album>) {
        this.albums = accountsResponse
        albums.size
        notifyDataSetChanged()
    }

    class ViewHolder(internal var binding: AlbumItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}
