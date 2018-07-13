package toluog.quickeats;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

class TableViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    String restaurantId;
    public TableViewModelFactory(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TablesViewModel(restaurantId);
    }
}
