package sh.mik.musixmatchtest.models.res.artist;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mik.sh on 01/07/2017.
 */

public class ArtistFields {

    private
    @SerializedName("artist_id")
    int id;

    private
    @SerializedName("artist_name")
    String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
