package toluog.quickeats

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.add_card_layout.*
import toluog.quickeats.model.Card

class AddCardActivity : AppCompatActivity() {

    private val TAG = AddCardActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_card_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.menu_done -> save()
        }
        return true
    }

    private fun save() {
        if(validate()) {
            Log.d(TAG, "Valid card")
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra("CARD", getCard())
            })
            finish()
        }
    }

    private fun validate(): Boolean {
        var status = true
        val cardNumber = card_no.text.toString()
        val date = month_date.text.toString()
        val CVC = cvc.text.toString()

        if(cardNumber.isBlank() || cardNumber.length < 16) {
            card_no.error = "Invalid card number"
            status = false
        }
        if(date.contains("/")) {
            val data = date.split("/")
            if(data[0].toInt() < 1 || data[0].toInt() > 31 || data[1].toInt() < 18) {
                month_date.error = "Invalid date"
                status = false
            }
        } else {
            month_date.error = "Invalid date"
            status = false
        }

        if(CVC.length < 3) {
            cvc.error = "Invalid CVC"
            status = false
        }
        return status
    }

    private fun getCard(): Card {
        val c = Card()
        val cNo = card_no.text.toString()
        val date = month_date.text.toString().split("/")
        val CVC = cvc.text.toString()
        return c.apply {
            cardNumber = cNo
            month = date[0].toInt()
            year = date[1].toInt()
            cvc = CVC
        }
    }
}
