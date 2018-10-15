/**
 * Copyright (C)
 */
package lastfm.grishman.com.lastfmapp.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import lastfm.grishman.com.lastfmapp.R
import lastfm.grishman.com.lastfmapp.databinding.ItemArtistBinding
import lastfm.grishman.com.lastfmapp.model.Artist

/**
 * Adapter for artists.
 */
class ArtistAdapter(private val clickListener: ArtistSelectListener) : RecyclerView.Adapter<ArtistAdapter.ViewHolder>() {

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
        holder.binding.item.setOnClickListener { clickListener.onArtistSelected(artists[position]) }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = artists.size


    fun swapItems(accountsResponse: List<Artist>) {
        this.artists = accountsResponse
        artists.size
        notifyDataSetChanged()
    }

    class ViewHolder(internal var binding: ItemArtistBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}
