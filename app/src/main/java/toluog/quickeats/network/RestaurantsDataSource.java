package toluog.quickeats.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import toluog.quickeats.model.Restaurant;

public class RestaurantsDataSource extends DataSource<Restaurant> {

    private final String TAG = RestaurantsDataSource.class.getSimpleName();
    private CollectionReference docRef;

    RestaurantsDataSource() {
        super();
        docRef = db.collection("restaurants");
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
                    dataList.add(r);
                    liveData.postValue(dataList);
                }
            }
        });
    }
}
