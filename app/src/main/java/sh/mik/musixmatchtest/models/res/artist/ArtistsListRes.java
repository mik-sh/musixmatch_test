package sh.mik.musixmatchtest.models.res.artist;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;

/**
 * Created by mik.sh on 01/07/2017.
 */

public class ArtistsListRes {

    private
    @SerializedName("artist_list")
    LinkedList<Artist> list;

    public LinkedList<Artist> getList() {
        return list;
    }
}
