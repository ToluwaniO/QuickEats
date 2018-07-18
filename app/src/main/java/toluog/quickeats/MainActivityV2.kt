package toluog.quickeats

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import com.miguelcatalan.materialsearchview.MaterialSearchView

import kotlinx.android.synthetic.main.activity_main_v2.*
import kotlinx.android.synthetic.main.content_main_activity_v2.*
import toluog.quickeats.model.Restaurant
import java.util.ArrayList

class MainActivityV2 : AppCompatActivity(), RestaurantRecyclerViewAdapter.RestaurantListener,
RestaurantsFragment.OnListFragmentInteractionListener{
    private var mColumnCount = 3

    private val TAG = MainActivityV2::class.java.simpleName
    private var restaurants = arrayListOf<Restaurant>()
    private lateinit var viewModel: RestaurantsViewModel
    private lateinit var adapter: RestaurantRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_v2)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this).get(RestaurantsViewModel::class.java)
        restaurants = ArrayList()
        adapter = RestaurantRecyclerViewAdapter(arrayListOf(), this)
        r_recycler.layoutManager = GridLayoutManager(this, mColumnCount)
        r_recycler.adapter = adapter

        search_view.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.search(newText ?: "", restaurants)
                return true
            }

        })


        viewModel.restaurants.observe(this, Observer { restaurants ->
            updateUi(restaurants)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        val item = menu.findItem(R.id.action_search)
        search_view.setMenuItem(item)

        return true
    }

    override fun onStart() {
        super.onStart()
        if (!FirebaseManager.isSignedIn()) {
            FirebaseManager.startSignIn(this)
        }
    }

    private fun updateUi(restaurants: List<*>?) {
        this.restaurants.clear()
        restaurants?.forEach {
            val r = it as Restaurant?
            if(r != null) {
                this.restaurants.add(r)
            }
        }
        adapter.update(restaurants)
    }

    override fun onClicked(restaurant: Restaurant) {
        val intent = Intent(this@MainActivityV2, TablesActivity::class.java)
        intent.putExtra("restaurant", restaurant)
        startActivity(intent)
    }

    override fun restaurantClicked(restaurant: Restaurant) {

    }

}
