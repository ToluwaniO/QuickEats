package toluog.quickeats

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_cards.*
import kotlinx.android.synthetic.main.card_item_layout.*
import toluog.quickeats.model.Card

class CardsActivity : AppCompatActivity() {

    companion object {
        val CARD_REQUEST_CODE = 111
    }
    private val TAG = CardsActivity::class.java.simpleName
    private val cards = arrayListOf<Card>()
    private var adapter = CardAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards)
        card_recycler.layoutManager = LinearLayoutManager(this)
        card_recycler.adapter = adapter
        updateView()
        add_fab.setOnClickListener {
            val intent = Intent(this@CardsActivity, AddCardActivity::class.java)
            startActivityForResult(intent, CARD_REQUEST_CODE)
        }
    }

    fun clicked() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun updateView() {
        if(cards.isEmpty()) {
            no_cards.visibility = View.VISIBLE
        } else {
            no_cards.visibility = View.GONE
            adapter.notifyDataSetChanged()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CARD_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val card = data?.extras?.get("CARD") as Card?
            Log.d(TAG, "$card")
            if(card != null) {
                cards.add(card)
                updateView()
            }
        }
    }

    inner class CardAdapter: RecyclerView.Adapter<CardAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.card_item_layout, parent, false)
            return ViewHolder(v)
        }

        override fun getItemCount() = cards.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(cards[position])
        }

        inner class ViewHolder(override val containerView: View)
            : RecyclerView.ViewHolder(containerView), LayoutContainer {

            fun bind(card: Card) {
                val len = card.cardNumber.lastIndex+1
                val a = len -4
                card_info.text = "Card ending in ${card.cardNumber.substring(a, len)}"

                containerView.setOnClickListener {
                    clicked()
                }
            }

        }

    }
}
