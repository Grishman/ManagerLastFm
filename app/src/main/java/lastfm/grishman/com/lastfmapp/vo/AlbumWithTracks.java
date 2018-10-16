package lastfm.grishman.com.lastfmapp.vo;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

/**
 * shit
 */
public class AlbumWithTracks {
    @Embedded
    public ViewAlbum album;
    @Relation(parentColumn = "id", entityColumn = "track_id", entity = ViewTracks.class)
    public List tracks;
}
