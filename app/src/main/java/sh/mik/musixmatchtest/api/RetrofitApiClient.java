package sh.mik.musixmatchtest.api;


import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sh.mik.musixmatchtest.config.Config;
import sh.mik.musixmatchtest.models.res.BaseResponse;
import sh.mik.musixmatchtest.models.res.artist.ArtistsListRes;
import sh.mik.musixmatchtest.models.res.track.TracksListRes;

/**
 * Created by mik.sh on 30/06/2017.
 */

public class RetrofitApiClient {

    private static RetrofitApiClient INSTANCE;

    private RetrofitApiInterface retrofitApi;

    private RetrofitApiClient() {
        init();
    }

    public static RetrofitApiClient getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RetrofitApiClient();
        }

        return INSTANCE;
    }

    private void init() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        retrofitApi = retrofit.create(RetrofitApiInterface.class);
    }

    private int getErrorCode(Throwable throwable) {

        if (throwable instanceof HttpException) {
            HttpException err = (HttpException) throwable;
            return err.code();
        }

        return 499;
    }

    public Observable<BaseResponse<ArtistsListRes>> getArtistsList(Map<String, String> options) {

        return retrofitApi.getArtistsList(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(err -> {
                    BaseResponse<ArtistsListRes> response = new BaseResponse<>();
                    response.setResCode(getErrorCode(err));
                    return Observable.just(response);
                });
    }

    public Observable<BaseResponse<TracksListRes>> getSongsList(Map<String, String> options) {

        return retrofitApi.getSongsList(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(err -> {
                    BaseResponse<TracksListRes> response = new BaseResponse<>();
                    response.setResCode(getErrorCode(err));
                    return Observable.just(response);
                });
    }
}
