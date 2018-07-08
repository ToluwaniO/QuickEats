package toluog.quickeats;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import toluog.quickeats.model.Restaurant;
import toluog.quickeats.network.Repository;

public class RestaurantsViewModel extends AndroidViewModel {
    private Repository repository;

    public RestaurantsViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(Repository.RepoType.RESTAURANT);
    }

    public LiveData<List> getRestaurants() {
        return  repository.get();
    }
}
