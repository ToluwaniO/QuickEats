package toluog.quickeats.network;

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
import toluog.quickeats.model.Table;

public class TablesDataSource extends DataSource<Table> {

    private final String TAG = TablesDataSource.class.getSimpleName();
    private CollectionReference docRef;

    public TablesDataSource(String tableId) {
        docRef = db.collection("restaurants").document(tableId).collection("tables");
        initData();
    }

    private void initData() {
        docRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                for (QueryDocumentSnapshot doc : snapshot) {
                    Log.d(TAG, doc.getId());
                    Table t = doc.toObject(Table.class);
                    t.setId(doc.getId());
                    dataList.add(t);
                    liveData.postValue(dataList);
                }
            }
        });
    }
}
