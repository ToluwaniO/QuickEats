package toluog.quickeats.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class DataSource<T> {
    protected FirebaseFirestore db;
    protected MutableLiveData<List<T>> liveData;
    protected List<T> dataList;

    DataSource() {
        db = FirebaseFirestore.getInstance();
        liveData = new MutableLiveData<>();
        dataList = new ArrayList<>();
    }

    public LiveData<List<T>> get() {
        return liveData;
    }
}
