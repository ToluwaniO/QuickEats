package toluog.quickeats

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import kotlinx.android.synthetic.main.activity_restaurants.*
import toluog.quickeats.model.Restaurant
import java.util.ArrayList
import com.miguelcatalan.materialsearchview.MaterialSearchView



class RestaurantsActivity : AppCompatActivity(), RestaurantRecyclerViewAdapter.RestaurantListener {

    private val mColumnCount = 3

    private val TAG = RestaurantsActivity::class.java.simpleName
    private var restaurants = arrayListOf<Restaurant>()
    private lateinit var viewModel: RestaurantsViewModel
    private lateinit var adapter: RestaurantRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurants)

        viewModel = ViewModelProviders.of(this).get(RestaurantsViewModel::class.java)
        restaurants = ArrayList()
        adapter = RestaurantRecyclerViewAdapter(arrayListOf(), this)
        r_recycler.layoutManager = GridLayoutManager(this, mColumnCount)
        r_recycler.adapter = adapter

        viewModel.restaurants.observe(this, Observer { restaurants ->
            updateUi(restaurants)
        })

        search_view.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //Do some magic
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.search(newText, restaurants)
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        val item = menu.findItem(R.id.action_search)
        search_view.setMenuItem(item)

        return true
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
        val intent = Intent(this@RestaurantsActivity, TablesActivity::class.java)
        intent.putExtra("restaurant", restaurant)
        startActivity(intent)
    }
}
