package toluog.quickeats

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import toluog.quickeats.model.Order


class OrderItemFragment: DialogFragment() {

    interface OrderListener {
        fun onDone(order: Order)
    }
    private var listener: OrderListener? = null
    private lateinit var itemName: TextInputEditText
    private lateinit var itemQuantity: TextInputEditText
    private lateinit var itemPrice: TextInputEditText

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

        itemName = view.findViewById(R.id.item_name)
        itemQuantity = view.findViewById(R.id.item_quantity)
        itemPrice = view.findViewById(R.id.item_price)
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
        val order = Order().apply {
            name = itemName.text.toString()
            quantity = itemPrice.text.toString().toInt()
            price = itemPrice.text.toString().toDouble()
        }
        listener?.onDone(order)
    }
}