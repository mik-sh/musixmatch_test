package sh.mik.musixmatchtest.api;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;
import sh.mik.musixmatchtest.models.res.artist.ArtistsListRes;
import sh.mik.musixmatchtest.models.res.BaseResponse;
import sh.mik.musixmatchtest.models.res.track.TracksListRes;

/**
 * Created by mik.sh on 30/06/2017.
 */

public interface RetrofitApiInterface {

    @GET("chart.artists.get")
    Observable<BaseResponse<ArtistsListRes>> getArtistsList(@QueryMap Map<String, String> options);

    @GET("chart.tracks.get")
    Observable<BaseResponse<TracksListRes>> getSongsList(@QueryMap Map<String, String> options);

}


