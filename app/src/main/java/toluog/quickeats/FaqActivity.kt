package toluog.quickeats

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DividerItemDecoration.VERTICAL
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bignerdranch.expandablerecyclerview.ChildViewHolder
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter
import com.bignerdranch.expandablerecyclerview.ParentViewHolder
import com.bignerdranch.expandablerecyclerview.model.Parent
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_faq.*
import kotlinx.android.synthetic.main.text_view_item.*
import toluog.quickeats.model.FAQ

class FaqActivity : AppCompatActivity() {

    private val faqs = arrayListOf<FAQ>(
            FAQ().apply {
                question = "How do I Order my Food?"
                answer = "You can only order your food when you have arrived at a restaurant. You" +
                        " will get assigned a table number once you have arrived at the restaurant." +
                        " Once you are all set-up, you can select your restaurant and table number" +
                        " and select the 'Make an order button'."
            },
            FAQ().apply {
                question = "Why was I charged even though I forgot to pay?"
                answer = "QuickEats offers an ethical and fair environment to both our valued" +
                        " restaurants and our valued customers. If an occupant hasn’t paid in a 5" +
                        " hour time frame, you will be charged through our auto charge system."
            },
            FAQ().apply {
                question = "Why are there available tables when the restaurant is full?"
                answer = "Tables only become unavailable when the requested amount of people seated" +
                        " have all arrived. Once you arrive at a restaurant, let your server know" +
                        " how many people you are expecting so she can assign the appropriate" +
                        " table to you."
            }
    )
    private val adapter = FaqAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)
        faq_recycler.adapter = adapter
        faq_recycler.layoutManager = LinearLayoutManager(this)
        faq_recycler.addItemDecoration(DividerItemDecoration(this, VERTICAL))
    }

    inner class FaqAdapter: ExpandableRecyclerAdapter<FAQ, String, QuestionViewHolder, AnswerViewHolder>(faqs) {
        override fun onCreateParentViewHolder(parentViewGroup: ViewGroup, viewType: Int): QuestionViewHolder {
            val v = LayoutInflater.from(parentViewGroup.context).inflate(R.layout.text_view_item,
                    parentViewGroup, false)
            return QuestionViewHolder(v)
        }

        override fun onBindChildViewHolder(childViewHolder: AnswerViewHolder, parentPosition: Int,
                                           childPosition: Int, child: String) {
            childViewHolder.bind(faqs[parentPosition])
        }

        override fun onBindParentViewHolder(parentViewHolder: QuestionViewHolder,
                                            parentPosition: Int, parent: FAQ) {
            parentViewHolder.bind(faqs[parentPosition])
        }

        override fun onCreateChildViewHolder(childViewGroup: ViewGroup, viewType: Int): AnswerViewHolder {
            val v = LayoutInflater.from(childViewGroup.context).inflate(R.layout.text_view_item,
                    childViewGroup, false)
            return AnswerViewHolder(v)
        }

    }

    inner class QuestionViewHolder(override val containerView: View)
        : ParentViewHolder<Parent<FAQ>, FAQ>(containerView), LayoutContainer {

        fun bind(faq: FAQ) {
            data_text.text = faq.question
            data_text.setTextColor(ContextCompat.getColor(this@FaqActivity, R.color.colorPrimaryText))
        }
    }

    inner class AnswerViewHolder(override val containerView: View)
        : ChildViewHolder<String>(containerView), LayoutContainer {

        fun bind(faq: FAQ) {
            data_text.text = faq.answer
        }

    }
}
