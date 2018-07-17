package toluog.quickeats.network;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import toluog.quickeats.model.Order;
import toluog.quickeats.model.Restaurant;
import toluog.quickeats.model.Table;
import toluog.quickeats.model.User;

public class TablesDataSource extends DataSource<Table> {

    private final String TAG = TablesDataSource.class.getSimpleName();
    private CollectionReference docRef;

    public TablesDataSource(String restaurantId) {
        docRef = db.collection("restaurants").document(restaurantId).collection("tables");
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
                    for (int i = 0; i < dataList.size(); i++) {
                        Table tb = dataList.get(i);
                        if(tb.getId().equals(t.getId())) {
                            dataList.remove(i);
                            break;
                        }
                    }
                    dataList.add(t);
                    liveData.postValue(dataList);
                }
            }
        });
    }

    public void addOrder(String rId, String tId, Table table) {
        DocumentReference ref = db.collection("restaurants").document(rId)
                .collection("tables").document(tId);
        addWithTransaction(ref, "orders", listToListOrdersMap(table.getOrders()));
    }

    public void addOccupant(String rId, String tId, Table table) {
        Log.d(TAG, "updateOccupants");
        DocumentReference ref = db.collection("restaurants").document(rId)
                .collection("tables").document(tId);
        addWithTransaction(ref, "occupants", listToListMap(table.getOccupants()));
    }

    private void addWithTransaction(final DocumentReference ref, final String key, final Object value) {
        db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                Log.d(TAG, "transaction started");
                transaction.update(ref, key, value);
                // Success
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Transaction success!");
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Transaction failure.", e);
            }
        });
    }

    private List<Map<Object, Object>> listToListMap(List<User> users) {
        List<Map<Object, Object>> a = new ArrayList<>();
        for(User u : users) {
            a.add(u.toMap());
        }
        return a;
    }

    private List<Map<Object, Object>> listToListOrdersMap(List<Order> orders) {
        List<Map<Object, Object>> a = new ArrayList<>();
        for(Order o : orders) {
            a.add(o.toMap());
        }
        return a;
    }
}
