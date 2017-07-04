package sh.mik.musixmatchtest.models.res.track;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;

/**
 * Created by mik.sh on 01/07/2017.
 */

public class TracksListRes {

    private
    @SerializedName("track_list")
    LinkedList<Track> list;

    public LinkedList<Track> getList() {
        return list;
    }
}
