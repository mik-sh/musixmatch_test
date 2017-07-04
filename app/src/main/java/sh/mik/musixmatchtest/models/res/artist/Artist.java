package sh.mik.musixmatchtest.models.res.artist;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mik.sh on 01/07/2017.
 */

public class Artist {

    private
    @SerializedName("artist")
    ArtistFields artist;

    public ArtistFields getArtist() {
        return artist;
    }
}
