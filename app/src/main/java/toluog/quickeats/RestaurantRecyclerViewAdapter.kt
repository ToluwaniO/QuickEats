package toluog.quickeats

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.restaurant_item_view.*
import toluog.quickeats.RestaurantsFragment.OnListFragmentInteractionListener
import toluog.quickeats.model.Restaurant

/**
 * [RecyclerView.Adapter] that can display a [] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class RestaurantRecyclerViewAdapter(private val restaurants: ArrayList<Restaurant>,
                                    private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<RestaurantRecyclerViewAdapter.ViewHolder>() {

    private val TAG = RestaurantRecyclerViewAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.restaurant_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(restaurants[position], mListener)
    }

    override fun getItemCount() = restaurants.size

    fun update(newList: List<*>?) {
        Log.d(TAG, "size = ${newList?.size ?: 0}")
        restaurants.clear()
        newList?.forEach {
            val r = it as Restaurant?
            if(r != null) {
                restaurants.add(r)
            }
        }
        notifyDataSetChanged()
    }

    fun search(query: String, restaurants: ArrayList<Restaurant>) {
        if(query.isBlank() || query.isEmpty()) {
            update(restaurants)
        } else {
            val newList = restaurants.filter {
                it.name.contains(query, true)
            }.toList()
            Log.d(TAG, query)
            Log.d(TAG, "$newList")
            update(newList)
        }
    }

    inner class ViewHolder(override val containerView: View)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {

        internal fun bind(restaurant: Restaurant, listener: OnListFragmentInteractionListener?) {
            restaurant_name.text = restaurant.name
            Glide.with(itemView.context)
                    .load(restaurant.imageUrl)
                    .into(restaurant_image)
            itemView.setOnClickListener { listener?.restaurantClicked(restaurant) }
        }

        override fun toString(): String {
            return ""
        }
    }
}
