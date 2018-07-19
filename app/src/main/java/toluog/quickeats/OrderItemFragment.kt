package toluog.quickeats

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import toluog.quickeats.model.MenuItem
import toluog.quickeats.model.Order
import android.widget.TextView
import android.view.LayoutInflater
import android.widget.ImageView
import java.time.DayOfWeek


class OrderItemFragment: DialogFragment() {

    interface OrderListener {
        fun onDone(order: Order)
    }
    private var listener: OrderListener? = null
    private lateinit var itemQuantity: TextInputEditText
    private lateinit var itemNotes: TextInputEditText
    private lateinit var spinner: Spinner

    private val menu = arrayListOf<MenuItem>(
            MenuItem("Fried Rice", 5.0),
            MenuItem("Shawarma", 13.0),
            MenuItem("Pizza", 11.0),
            MenuItem("Sushi", 7.0)
    )

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        // Get the layout inflater
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.fragment_order_item, null)
        builder.setView(view)
                .setPositiveButton("DONE") { dialog, which ->
                    updateOrder()
                }
                .setNegativeButton("CANCEL") { dialog, which ->
                    dismiss()
                }

//        itemName = view.findViewById(R.id.item_name)
        itemQuantity = view.findViewById(R.id.item_quantity)
        itemNotes = view.findViewById(R.id.item_notes)
        spinner = view.findViewById(R.id.menu_spinner)
        spinner.adapter = SpinnerAdapter(activity, R.layout.spinner_item_layout, menu)
        return builder.create()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OrderListener) {
            listener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun updateOrder() {
        val pos = spinner.selectedItemPosition
        val item = menu[pos]
        val order = Order().apply {
            name = item.name
            quantity = itemQuantity.text.toString().toInt()
            price = item.price
            notes = itemNotes.text.toString()
        }
        listener?.onDone(order)
    }

    inner class SpinnerAdapter(context: Context?, view: Int, items: List<MenuItem>)
        : ArrayAdapter<MenuItem>(context, view, items) {

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
            return getCustomView(position, convertView, parent)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            return getCustomView(position, convertView, parent)
        }

        private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val row = layoutInflater.inflate(R.layout.spinner_item_layout, parent, false)
            val name = row.findViewById(R.id.item_name) as TextView
            val price = row.findViewById(R.id.item_price) as TextView
            name.text = menu[position].name
            price.text = "$${menu[position].price}"
            return row
        }
    }
}