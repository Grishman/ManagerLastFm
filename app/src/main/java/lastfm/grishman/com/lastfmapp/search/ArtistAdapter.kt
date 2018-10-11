/**
 * Copyright (C)
 */
package lastfm.grishman.com.lastfmapp.search

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import lastfm.grishman.com.lastfmapp.R
import lastfm.grishman.com.lastfmapp.databinding.ItemArtistBinding
import lastfm.grishman.com.lastfmapp.model.Artist

/**
 * Adapter for artists.
 */
class ArtistAdapter : RecyclerView.Adapter<ArtistAdapter.ViewHolder>() {

    private var artists: List<Artist> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val artistItemBinding = DataBindingUtil.inflate<ItemArtistBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_artist,
                parent,
                false)
        return ViewHolder(artistItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.viewModel = artists[position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = artists.size


    fun swapItems(accountsResponse: List<Artist>) {
        this.artists = accountsResponse
        artists.size
        notifyDataSetChanged()
    }

    class ViewHolder(internal var binding: ItemArtistBinding) : RecyclerView.ViewHolder(item :ItemArtistBinding){

    }
}
