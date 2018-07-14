package toluog.quickeats

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_tables.*
import kotlinx.android.synthetic.main.table_item_layout.*
import toluog.quickeats.model.Restaurant
import toluog.quickeats.model.Table

class TablesActivity : AppCompatActivity() {

    private val TAG = TablesActivity::class.java.simpleName

    private lateinit var restaurant: Restaurant
    private val tables = arrayListOf<Table>()
    private lateinit var viewModel: TablesViewModel
    private lateinit var adapter: TablesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tables)

        restaurant = intent.extras.get("restaurant") as Restaurant
        Log.d(TAG, restaurant.id)

        adapter = TablesAdapter()
        table_recycler.adapter = adapter
        table_recycler.layoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProviders.of(this, TableViewModelFactory(restaurant.id))
                .get(TablesViewModel::class.java)

        viewModel.tables.observe(this, Observer { list ->
            tables.clear()
            list?.forEach {
                if(it != null) {
                    tables.add(it as Table)
                }
            }
            adapter.notifyDataSetChanged()
        })
//
//        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener, android.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                if(newText != null) {
//                    adapter.search(newText)
//                }
//                return true
//            }
//
//        })
    }

    fun openTable(table: Table) {
        val intent = Intent(this@TablesActivity, TableActivity::class.java)
        intent.putExtra("restaurant", restaurant)
        intent.putExtra("tableId", table.id)
        startActivity(intent)
    }

    internal inner class TablesAdapter : RecyclerView.Adapter<TablesAdapter.ViewHolder>() {
        private val adapterList = arrayListOf<Table>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.table_item_layout, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.updateUi(tables[position])
        }

        override fun getItemCount(): Int {
            return tables.size
        }


        inner class ViewHolder(override val containerView: View)
            : RecyclerView.ViewHolder(containerView), LayoutContainer {

            fun updateUi(table: Table) {
                table_name.text = "TABLE " + table.id
                if (table.isOccupied) {
                    occupied_state.text = "OCCUPIED"
                } else {
                    occupied_state.text = "FREE"
                }
                total_text.text = "$" + table.total

                itemView.setOnClickListener { openTable(table) }
            }
        }
    }
}
