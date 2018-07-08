package toluog.quickeats;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import toluog.quickeats.model.Table;

public class TablesActivity extends AppCompatActivity {

    private String TAG = TablesActivity.class.getSimpleName();

    @BindView(R.id.table_recycler)
    RecyclerView recyclerView;

    String tableId;
    List<Table> tables;
    TablesViewModel viewModel;
    TablesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);
        ButterKnife.bind(this);

        tableId = getIntent().getStringExtra("tableId");
        Log.d(TAG, tableId);

        tables = new ArrayList<>();
        adapter = new TablesAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewModel = ViewModelProviders.of(this, new TableViewModelFactory(tableId))
                .get(TablesViewModel.class);

        viewModel.getTables().observe(this, new Observer<List>() {
            @Override
            public void onChanged(@Nullable List list) {
                tables.clear();
                tables.addAll(list);
                adapter.notifyDataSetChanged();
            }
        });
    }

    class TablesAdapter extends RecyclerView.Adapter<TablesAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.table_item_layout, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.updateUi(tables.get(position));
        }

        @Override
        public int getItemCount() {
            return tables.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.table_name)
            TextView tableNameView;
            @BindView(R.id.occupied_state)
            TextView stateView;
            @BindView(R.id.total_text)
            TextView totalView;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void updateUi(Table table) {
                tableNameView.setText("TABLE " + table.getId());
                if(table.isOccupied()) {
                    stateView.setText("OCCUPIED");
                } else {
                    stateView.setText("FREE");
                }
                totalView.setText("$" + table.getTotal());
            }
        }
    }

    class TableViewModelFactory extends ViewModelProvider.NewInstanceFactory {
        String tableId;
        public TableViewModelFactory(String tableId) {
            this.tableId = tableId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new TablesViewModel(tableId);
        }
    }
}
