package sh.mik.musixmatchtest.screens.artists_list;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.subscriptions.CompositeSubscription;
import sh.mik.musixmatchtest.api.RetrofitApiClient;
import sh.mik.musixmatchtest.config.Config;
import sh.mik.musixmatchtest.models.internal.Card;
import sh.mik.musixmatchtest.models.req.RequestField;
import sh.mik.musixmatchtest.models.res.BaseResponse;
import sh.mik.musixmatchtest.models.res.artist.Artist;
import sh.mik.musixmatchtest.models.res.artist.ArtistFields;
import sh.mik.musixmatchtest.models.res.artist.ArtistsListRes;
import sh.mik.musixmatchtest.models.res.track.Track;
import sh.mik.musixmatchtest.models.res.track.TrackFields;
import sh.mik.musixmatchtest.models.res.track.TracksListRes;

/**
 * Created by mik.sh on 01/07/2017.
 */

public class ArtistsListPresenter implements ArtistsListContract.Presenter {

    private RetrofitApiClient retrofitApi;

    private ArtistsListContract.View view;

    private CompositeSubscription subscription = new CompositeSubscription();

    public ArtistsListPresenter(RetrofitApiClient retrofitApi, ArtistsListContract.View view) {
        this.retrofitApi = retrofitApi;
        this.view = view;

        view.setPresenter(this);
    }

    public void loadList() {

        List<Card> cardsLists = new ArrayList<>();

        HashMap<String, String> request = new HashMap<>();

        request.put(RequestField.API_KEY.toString(), Config.API_KEY);
        request.put(RequestField.PAGE_SIZE.toString(), Config.LIST_LIMIT);
        request.put(RequestField.COUNTRY.toString(), Config.COUNTRY);

        Observable<BaseResponse<ArtistsListRes>> loadArtists =
                retrofitApi.getArtistsList(request);

        Observable<BaseResponse<TracksListRes>> loadTracks =
                retrofitApi.getSongsList(request);

        Observable<Pair<BaseResponse<ArtistsListRes>, BaseResponse<TracksListRes>>> loadedLists =
                Observable.zip(loadArtists, loadTracks, Pair::new)
                        .share();

        Observable<Pair<BaseResponse<ArtistsListRes>, BaseResponse<TracksListRes>>> badResponse =
                loadedLists
                        .filter(lists -> lists.first.getCode() != 200 || lists.second.getCode() != 200)
                        .doOnNext(err -> errorCase());

        Observable<Pair<BaseResponse<ArtistsListRes>, BaseResponse<TracksListRes>>> fineResponse =
                loadedLists
                        .filter(lists -> lists.first.getCode() == 200 && lists.second.getCode() == 200);

        Observable<Pair<BaseResponse<ArtistsListRes>, BaseResponse<TracksListRes>>> mergeLists =
                fineResponse
                        .doOnNext(lists -> {

                            LinkedList<Artist> artists = new LinkedList<>(lists.first.getBody().getList());
                            LinkedList<Track> tracks = new LinkedList<>(lists.second.getBody().getList());

                            for (Artist artist : artists) {
                                ArtistFields artistItem = artist.getArtist();
                                cardsLists.add(new Card(Card.CardType.ARTIST, artistItem.getName(), null));

                                for (Track track : new ArrayList<>(tracks)) {
                                    TrackFields trackItem = track.getTrack();

                                    if (trackItem.getArtistId() == artistItem.getId()) {
                                        cardsLists.add(new Card(Card.CardType.TRACK, trackItem.getName(), trackItem.getPictute()));
                                        tracks.remove(track);
                                    }
                                }
                            }

                            successCase(cardsLists);
                        });

        Observable startSubscriptions = Observable.merge(
                badResponse,
                mergeLists
        );

        subscription.add(startSubscriptions.subscribe());
    }

    @Override
    public void postRestoredList(List<Card> list) {

        if (!list.isEmpty()) {
            successCase(list);
        } else {
            errorCase();
        }
    }

    @Override
    public void clearSubscriptions() {
        subscription.clear();
    }

    private void errorCase() {
        view.showError();
        view.showEmptyList(true);
        view.showProgress(false);
        view.stopRefreshingAnimation();
    }

    private void successCase(List<Card> list) {
        view.showEmptyList(false);
        view.showProgress(false);
        view.stopRefreshingAnimation();
        view.updateList(list);
    }
}
