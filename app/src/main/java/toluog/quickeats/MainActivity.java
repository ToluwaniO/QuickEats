package toluog.quickeats;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import toluog.quickeats.model.Restaurant;

public class MainActivity extends AppCompatActivity
        implements RestaurantRecyclerViewAdapter.RestaurantListener,
        RestaurantsFragment.OnListFragmentInteractionListener {

    @BindView(R.id.fragment_frame)
    FrameLayout fragmentFrame;
    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        ButterKnife.bind(this);
        searchView = findViewById(R.id.search_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,
                RestaurantsFragment.Companion.newInstance(2)).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.action_search);

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!FirebaseManager.isSignedIn()) {
            FirebaseManager.startSignIn(this);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClicked(@NotNull Restaurant restaurant) {
        Intent intent = new Intent(MainActivity.this, TablesActivity.class);
        intent.putExtra("restaurant", restaurant);
        startActivity(intent);
    }

    @Override
    public void restaurantClicked(@NotNull Restaurant restaurant) {

    }
}
