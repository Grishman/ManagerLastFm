package lastfm.grishman.com.lastfmapp.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(tableName = "tracks_table",
        primaryKeys = ["track_id", "name"],
        indices = [(Index("name"))])
data class ViewTracks(@ColumnInfo(name = "track_id")
                      val trackId: Long = 0L,
                      val name: String,
                      val url: String,
                      val duration: Int) {
}