package toluog.quickeats.network;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;
import javax.annotation.Nullable;
import toluog.quickeats.model.Restaurant;
import toluog.quickeats.model.Table;
import toluog.quickeats.network.RestaurantsDataSource;

public class Repository {

    private String TAG = Repository.class.getSimpleName();
    private DataSource source;
    private RepoType type;
    private String restaurantId;

    public Repository(RepoType repoType) {
        setup(repoType);
    }

    public Repository(RepoType repoType, String restaurantId) {
        this.restaurantId = restaurantId;
        setup(repoType);
    }

    @Nullable
    public LiveData<List> get() {
        return source.get();
    }

    public void updateOccupants(String rId, String tId, Table table) {
        Log.d(TAG, "updateOccupants");
        TablesDataSource s = (TablesDataSource) source;
        s.addOccupant(rId, tId, table);
    }

    public void updateOrders(String rId, String tId, Table table) {
        TablesDataSource s = (TablesDataSource) source;
        s.addOrder(rId, tId, table);
    }

    private void setup(RepoType repoType) {
        if(repoType == RepoType.RESTAURANT) {
            source = new RestaurantsDataSource();
        } else if(repoType == RepoType.TABLE) {
            source = new TablesDataSource(restaurantId);
        }
    }

    public enum RepoType {
        RESTAURANT, TABLE
    }

}
