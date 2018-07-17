package toluog.quickeats;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import toluog.quickeats.model.Restaurant;

public class MainActivity extends AppCompatActivity
        implements RestaurantsFragment.OnListFragmentInteractionListener {

    @BindView(R.id.fragment_frame)
    FrameLayout fragmentFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,
                RestaurantsFragment.Companion.newInstance(2)).commit();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void restaurantClicked(Restaurant restaurant) {
        Intent intent = new Intent(MainActivity.this, TablesActivity.class);
        intent.putExtra("restaurant", restaurant);
        startActivity(intent);
    }
}
