package sh.mik.musixmatchtest.screens.artists_list;

import java.util.List;

import sh.mik.musixmatchtest.models.internal.Card;

/**
 * Created by mik.sh on 01/07/2017.
 */

public interface ArtistsListContract {

    interface View {

        void setPresenter(Presenter presenter);

        void showProgress(boolean show);

        void showEmptyList(boolean show);

        void updateList(List<Card> list);

        void showError();

        void stopRefreshingAnimation();
    }

    interface Presenter {

        void loadList();

        void postRestoredList(List<Card> list);

        void clearSubscriptions();
    }
}
