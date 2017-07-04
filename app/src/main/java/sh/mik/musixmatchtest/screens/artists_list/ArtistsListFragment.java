package sh.mik.musixmatchtest.screens.artists_list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sh.mik.musixmatchtest.R;
import sh.mik.musixmatchtest.adapters.ArtistsListAdapter;
import sh.mik.musixmatchtest.models.internal.Card;

/**
 * Created by mik.sh on 01/07/2017.
 */

public class ArtistsListFragment extends Fragment
        implements ArtistsListContract.View {

    private View rootView;

    private ArtistsListContract.Presenter presenter;

    private ArtistsListAdapter adapter;

    private List<Card> cardsList = new ArrayList<>();

    private String LIST_DATA = "LIST_DATA";

    @BindView(R.id.artists_list_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.artists_list_progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.artists_list_empty_list_message)
    TextView emptyListMessage;

    @BindView(R.id.artists_list_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    public ArtistsListFragment() {

    }

    public static ArtistsListFragment newInstance() {

        Bundle args = new Bundle();

        ArtistsListFragment fragment = new ArtistsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_artists_list, container, false);

        ButterKnife.bind(this, rootView);

        recyclerViewSetUp();

        initListeners();

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putSerializable(LIST_DATA, (Serializable) cardsList);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            presenter.postRestoredList((List<Card>) savedInstanceState.getSerializable(LIST_DATA));
        } else {
            presenter.loadList();
        }
    }

    @Override
    public void onDestroyView() {
        presenter.clearSubscriptions();
        super.onDestroyView();
    }

    private void initListeners() {
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.loadList());
    }

    private void recyclerViewSetUp() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ArtistsListAdapter(cardsList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setPresenter(ArtistsListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showEmptyList(boolean show) {
        emptyListMessage.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void updateList(List<Card> list) {
        this.cardsList.clear();
        this.cardsList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        String err = getString(R.string.error);
        Snackbar.make(rootView, err, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void stopRefreshingAnimation() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
