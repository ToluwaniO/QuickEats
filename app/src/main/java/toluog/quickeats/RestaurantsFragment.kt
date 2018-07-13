package toluog.quickeats

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import java.util.ArrayList

import kotlinx.android.synthetic.main.fragment_restaurants_layout.*
import toluog.quickeats.model.Restaurant

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnListFragmentInteractionListener]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class RestaurantsFragment : Fragment() {
    private var mColumnCount = 2
    private var mListener: OnListFragmentInteractionListener? = null

    private var restaurants = arrayListOf<Restaurant>()
    private lateinit var viewModel: RestaurantsViewModel
    private lateinit var adapter: RestaurantRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mColumnCount = arguments!!.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_restaurants_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(RestaurantsViewModel::class.java)
        restaurants = ArrayList()
        adapter = RestaurantRecyclerViewAdapter(restaurants, mListener)
        r_recycler.layoutManager = GridLayoutManager(view.context, 2)
        r_recycler.adapter = adapter

        viewModel.restaurants.observe(this, Observer { restaurants -> updateUi(restaurants) })
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    private fun updateUi(restaurants: List<*>?) {
        this.restaurants.clear()
        restaurants?.forEach {
            val r = it as Restaurant?
            if(r != null) {
                this.restaurants.add(r)
            }
        }
        adapter.notifyDataSetChanged()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnListFragmentInteractionListener {
        fun restaurantClicked(restaurant: Restaurant)
    }

    companion object {

        private val ARG_COLUMN_COUNT = "column-count"

        fun newInstance(columnCount: Int): RestaurantsFragment {
            val fragment = RestaurantsFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }
}
