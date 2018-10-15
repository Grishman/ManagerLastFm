/**
 * Copyright (C)
 */
package lastfm.grishman.com.lastfmapp.albumDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import lastfm.grishman.com.lastfmapp.R
import lastfm.grishman.com.lastfmapp.databinding.TrackItemBinding
import lastfm.grishman.com.lastfmapp.model.album.Track

/**
 * Adapter for tracks.
 */
class TracksAdapter() : RecyclerView.Adapter<TracksAdapter.ViewHolder>() {

    private var tracks: List<Track> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = DataBindingUtil.inflate<TrackItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.track_item,
                parent,
                false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.viewModel = tracks[position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = tracks.size


    fun swapItems(newData: List<Track>) {
        this.tracks = newData
        notifyDataSetChanged()
    }

    class ViewHolder(internal var binding: TrackItemBinding) : RecyclerView.ViewHolder(binding.root)
}
