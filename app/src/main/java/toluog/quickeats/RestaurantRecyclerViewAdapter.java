package toluog.quickeats;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import toluog.quickeats.RestaurantsFragment.OnListFragmentInteractionListener;
import toluog.quickeats.model.DummyContent.DummyItem;
import toluog.quickeats.model.Restaurant;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class RestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantRecyclerViewAdapter.ViewHolder> {

    private final List<Restaurant> restaurants;
    private final OnListFragmentInteractionListener mListener;

    RestaurantRecyclerViewAdapter(List<Restaurant> items, OnListFragmentInteractionListener listener) {
        restaurants = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.bind(restaurants.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.restaurant_image)
        ImageView restaurantImageView;
        @BindView(R.id.restaurant_name)
        TextView restaurantNameView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(final Restaurant restaurant, final OnListFragmentInteractionListener listener) {
            restaurantNameView.setText(restaurant.getName());
            Glide.with(itemView.getContext())
                    .load(restaurant.getImageUrl())
                    .into(restaurantImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.restaurantClicked(restaurant);
                }
            });
        }

        @Override
        public String toString() {
            return "";
        }
    }
}
