package toluog.quickeats;

import android.arch.lifecycle.LiveData;
import java.util.List;
import javax.annotation.Nullable;
import toluog.quickeats.model.Restaurant;

public class Repository {

    private RestaurantsDataSource restaurantsDataSource;

    Repository(RepoType repoType) {
        setup(repoType);
    }

    @Nullable
    public LiveData<List<Restaurant>> getRestaurants() {
        return restaurantsDataSource.getRestaurants();
    }

    private void setup(RepoType repoType) {
        if(repoType == RepoType.RESTAURANT) {
            restaurantsDataSource = new RestaurantsDataSource();
        }
    }

    enum RepoType {
        RESTAURANT, TABLE
    }


}
