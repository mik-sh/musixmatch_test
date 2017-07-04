package sh.mik.musixmatchtest.models.res.track;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mik.sh on 01/07/2017.
 */

public class Track {

    private
    @SerializedName("track")
    TrackFields track;

    public TrackFields getTrack() {
        return track;
    }
}
