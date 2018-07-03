package toluog.quickeats;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import toluog.quickeats.model.Restaurant;

public class RestaurantsDataSource {

    private final String TAG = RestaurantsDataSource.class.getSimpleName();
    private FirebaseFirestore db;
    private CollectionReference docRef;
    private MutableLiveData<List<Restaurant>> liveRestaurants;
    private List<Restaurant> restaurants;

    RestaurantsDataSource() {
        db = FirebaseFirestore.getInstance();
        docRef = db.collection("restaurants");
        restaurants = new ArrayList<>();
        liveRestaurants = new MutableLiveData<>();
        initData();
    }

    private void initData() {
        docRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                for (QueryDocumentSnapshot doc : snapshots) {
                    Restaurant r = doc.toObject(Restaurant.class);
                    r.setId(doc.getId());
                    restaurants.add(r);
                    liveRestaurants.postValue(restaurants);
                }
            }
        });
    }

    public LiveData<List<Restaurant>> getRestaurants() {
        return liveRestaurants;
    }
}
