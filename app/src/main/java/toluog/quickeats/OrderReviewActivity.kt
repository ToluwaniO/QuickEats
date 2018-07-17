package toluog.quickeats

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DividerItemDecoration.VERTICAL
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_order_review.*
import kotlinx.android.synthetic.main.receipt_item_layout.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.selector
import org.jetbrains.anko.toast
import toluog.quickeats.model.Order
import toluog.quickeats.model.Table

class OrderReviewActivity : AppCompatActivity() {

    private lateinit var table: Table
    private val adapter = ReceiptAdapter()
    private val payTypes = listOf("Split bill", "Pay all")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_review)

        table = intent.extras.getParcelable("table")
        receipt_recycler.adapter = adapter
        receipt_recycler.layoutManager = LinearLayoutManager(this)
        receipt_recycler.addItemDecoration(DividerItemDecoration(this, VERTICAL))
        var total = 0.0
        table.orders.forEach {
            total += it.price * it.quantity
        }
        total_value_view.text = "$$total"
        adapter.notifyDataSetChanged()

        pay_button.setOnClickListener {
            processPayType()
        }
    }
     private fun processPayType() {
         selector("How would you like to pay?", payTypes) { _, i ->
             if(i == 0) {
                 payNow(true)
             } else {
                 payNow()
             }
         }
     }

    private fun payNow(split: Boolean = false) {
        var toPay = 0.0
        table.orders.forEach {
            toPay += it.price * it.quantity
        }
        if(split) toPay /= table.occupants.size

        alert("You bill is $$toPay. Are you ready to pay?") {
            positiveButton("YES") {
                startActivityForResult(intentFor<CardsActivity>(), CardsActivity.CARD_REQUEST_CODE)
            }
            negativeButton("NO") {
                it.dismiss()
            }
        }.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CardsActivity.CARD_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            setResult(Activity.RESULT_OK)
            finish()
            toast("Valid card!")
        } else {
            toast("Could not get card")
        }
    }

    inner class ReceiptAdapter: RecyclerView.Adapter<ReceiptAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.receipt_item_layout, parent,
                    false)
            return ViewHolder(v)
        }

        override fun getItemCount() = table.orders.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(table.orders[position])
        }

        inner class ViewHolder(override val containerView: View)
            : RecyclerView.ViewHolder(containerView), LayoutContainer {

            fun bind(order: Order) {
                val quan = if(order.quantity == 1) {
                    "${order.quantity} item"
                } else {
                    "${order.quantity} items"
                }
                val priceText = "$${order.quantity * order.price}"
                item_name.text = order.name
                item_quantity.text = quan
                item_price.text = priceText
            }

        }

    }
}
