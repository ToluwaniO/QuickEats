package toluog.quickeats.network;

import android.arch.lifecycle.LiveData;
import java.util.List;
import javax.annotation.Nullable;
import toluog.quickeats.model.Restaurant;
import toluog.quickeats.network.RestaurantsDataSource;

public class Repository {

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
