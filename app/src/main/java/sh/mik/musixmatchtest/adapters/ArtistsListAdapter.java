package sh.mik.musixmatchtest.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sh.mik.musixmatchtest.R;
import sh.mik.musixmatchtest.models.internal.Card;

/**
 * Created by mik.sh on 13/02/2017.
 */

public class ArtistsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Card> cardsLists;

    static class ArtistViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.artist_name_text)
        TextView artistName;

        ArtistViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class TrackViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.track_name_text)
        TextView trackName;

        TrackViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public ArtistsListAdapter(List<Card> cardsLists) {
        this.cardsLists = cardsLists;
    }

    @Override
    public int getItemViewType(int position) {
        return cardsLists.get(position).getType() == Card.CardType.ARTIST ? 1 : 2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView;

        switch (viewType) {
            case 1:
                rootView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_artist, parent, false);
                return new ArtistViewHolder(rootView);
            default:
                rootView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_track, parent, false);
                return new TrackViewHolder(rootView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case 1:
                ArtistViewHolder artistViewHolder = (ArtistViewHolder) holder;
                artistViewHolder.artistName.setText(cardsLists.get(position).getName());
                break;

            case 2:
                TrackViewHolder trackViewHolder = (TrackViewHolder) holder;
                trackViewHolder.trackName.setText(cardsLists.get(position).getName());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return cardsLists.size();
    }
}
