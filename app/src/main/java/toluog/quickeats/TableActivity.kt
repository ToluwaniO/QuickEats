package toluog.quickeats

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_table.*
import kotlinx.android.synthetic.main.table_occupant_layout.*
import kotlinx.android.synthetic.main.table_order_item_layout.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import toluog.quickeats.model.Order
import toluog.quickeats.model.Restaurant
import toluog.quickeats.model.Table
import toluog.quickeats.model.User

class TableActivity : AppCompatActivity(), OrderItemFragment.OrderListener {

    private val TAG = TableActivity::class.java.simpleName
    private var viewModel: TablesViewModel? = null
    private lateinit var tableId: String
    private lateinit var restaurant: Restaurant
    private var table: Table? = null
    private var occupantAdapter = InnerAdapter(arrayListOf())
    private var ordersAdapter = InnerAdapter(arrayListOf())
    private var fabExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)
        restaurant = intent.extras.get("restaurant") as Restaurant
        tableId = intent.getStringExtra("tableId")

        col_bar_layout.title = restaurant.name
        col_bar_layout.setExpandedTitleColor(resources.getColor(android.R.color.transparent))
        col_bar_layout.setCollapsedTitleTextColor(ContextCompat.getColor(this,
                android.R.color.white))
        occupants_recycler.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false)
        occupants_recycler.adapter = occupantAdapter
        orders_recycler.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false)
        orders_recycler.adapter = ordersAdapter

        Glide.with(this).load(restaurant.imageUrl).into(restaurant_logo)

        main_fab.setOnClickListener {
            if(fabExpanded) {
                add_order_fab.visibility = View.GONE
                join_fab.visibility = View.GONE
                fabExpanded = false
                main_fab.setImageResource(R.drawable.ic_add )
            } else {
                add_order_fab.visibility = View.VISIBLE
                join_fab.visibility = View.VISIBLE
                fabExpanded = true
                main_fab.setImageResource(R.drawable.ic_close)
            }
        }

        add_order_fab.setOnClickListener {
            OrderItemFragment().show(supportFragmentManager, null)
        }

        join_fab.setOnClickListener {
            Log.d(TAG, "Join fab clicked")
            val user = FirebaseManager.user()
            viewModel?.updateOccupants(restaurant.id, tableId, table.apply {
                this?.occupants?.add(user)
            })
        }

        pay_now.setOnClickListener {
            payNow()
        }

        viewModel = ViewModelProviders.of(this, TableViewModelFactory(restaurant.id))
                .get(TablesViewModel::class.java)
        viewModel?.tables?.observe(this, Observer {
            val table = it?.find {
                val t = it as Table?
                t?.id == tableId
            } as Table?
            Log.d(TAG, "This $table found")
            updateUi(table)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CardsActivity.CARD_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            toast("Valid card!")
        } else {
            toast("Could not get card")
        }
    }

    private fun payNow() {
        val t = table ?: return
        var toPay = 0.0
        t.orders.forEach {
            toPay += it.price * it.quantity
        }

        alert("You bill is $$toPay. Are you ready to pay?") {
            positiveButton("YES") {
                startActivityForResult(intentFor<CardsActivity>(), CardsActivity.CARD_REQUEST_CODE)
            }
            negativeButton("NO") {
                it.dismiss()
            }
        }.show()
    }

    private fun updateUi(table: Table?) {
        this.table = table
        if(table == null) return
        main.visibility = View.VISIBLE
        table_name.text = "TABLE ${table.id}"
        occupantAdapter.update(table.occupants)
        ordersAdapter.update(table.orders)
        var total = 0.0
        table.orders.forEach {
            total += it.price * it.quantity
        }
        total_view.text = "$$total"
    }

    override fun onDone(order: Order) {
        table?.orders?.add(order)
        viewModel?.updateOrders(restaurant.id, tableId, table)
    }

    inner class InnerAdapter(var items: ArrayList<Any>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            if(viewType == OCCUPANT_TYPE) {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.table_occupant_layout,
                        parent, false)
                return OccupantViewHolder(v)
            }
            val v = LayoutInflater.from(parent.context).inflate(R.layout.table_order_item_layout,
                    parent, false)
            return OrderViewHolder(v)
        }

        override fun getItemCount() = items.size

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val type = getItemViewType(position)
            if(type == OCCUPANT_TYPE) {
                (holder as OccupantViewHolder).bind(items[position] as User)
            } else {
                (holder as OrderViewHolder).bind(items[position] as Order)
            }
        }

        override fun getItemViewType(position: Int): Int {
            val item = items[position]
            if(item is Order) return ORDER_TYPE
            return OCCUPANT_TYPE
        }

        fun update(newItems: List<Any>) {
            items.clear()
            items.addAll(newItems)
            notifyDataSetChanged()
        }

        inner class OccupantViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
                LayoutContainer {

            fun bind(user: User) {
                display_name.text = user.displayName
                email_address.text = user.email
            }

        }

        inner class OrderViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
                LayoutContainer {

            fun bind(order: Order) {
                item_name.text = order.name
                item_quantity.text = "Quantity: ${order.quantity}"
            }

        }



    }

    companion object {
        val ORDER_TYPE = 1
        val OCCUPANT_TYPE = 2
    }
}
