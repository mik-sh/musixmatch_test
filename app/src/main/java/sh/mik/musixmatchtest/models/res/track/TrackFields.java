package sh.mik.musixmatchtest.models.res.track;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mik.sh on 01/07/2017.
 */

public class TrackFields {

    private
    @SerializedName("track_name")
    String name;

    private
    @SerializedName("artist_id")
    int artistId;

    private
    @SerializedName("album_coverart_100x100")
    String pictute;

    public int getArtistId() {
        return artistId;
    }

    public String getName() {
        return name;
    }

    public String getPictute() {
        return pictute;
    }
}
