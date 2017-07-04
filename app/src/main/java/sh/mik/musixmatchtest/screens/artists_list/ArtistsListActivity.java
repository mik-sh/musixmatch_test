package sh.mik.musixmatchtest.screens.artists_list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import sh.mik.musixmatchtest.R;
import sh.mik.musixmatchtest.api.RetrofitApiClient;
import sh.mik.musixmatchtest.utils.NavigationUtils;

public class ArtistsListActivity extends AppCompatActivity {

    private RetrofitApiClient apiClient;

    private ArtistsListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiClient = RetrofitApiClient.getInstance();

        ArtistsListFragment clientsFragment =
                (ArtistsListFragment) getSupportFragmentManager().findFragmentById(R.id.artists_list_container);
        if (clientsFragment == null) {
            clientsFragment = ArtistsListFragment.newInstance();

            NavigationUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    clientsFragment,
                    R.id.artists_list_container
            );
        }

        presenter = new ArtistsListPresenter(
                apiClient,
                clientsFragment);
    }
}
