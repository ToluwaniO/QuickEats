package toluog.quickeats;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import toluog.quickeats.network.Repository;

public class TablesViewModel extends ViewModel {
    private Repository repository;

    public TablesViewModel(String tableId) {
        repository = new Repository(Repository.RepoType.TABLE, tableId);
    }


    public LiveData<List> getTables() {
        return repository.get();
    }
}
