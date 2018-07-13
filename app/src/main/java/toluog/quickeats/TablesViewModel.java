package toluog.quickeats;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import toluog.quickeats.model.Table;
import toluog.quickeats.network.Repository;

public class TablesViewModel extends ViewModel {
    private String TAG = TablesViewModel.class.getSimpleName();
    private Repository repository;

    public TablesViewModel(String restaurantId) {
        repository = new Repository(Repository.RepoType.TABLE, restaurantId);
    }


    public LiveData<List> getTables() {
        return repository.get();
    }

    public void updateOccupants(String rId, String tId, Table table) {
        Log.d(TAG, "addOccupant");
        repository.updateOccupants(rId, tId, table);
    }

    public void updateOrders(String rId, String tId, Table table) {
        Log.d(TAG, "addOrder");
        repository.updateOrders(rId, tId, table);
    }

}
