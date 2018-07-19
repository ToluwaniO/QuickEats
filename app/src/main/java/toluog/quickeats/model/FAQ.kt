package toluog.quickeats.model

import com.bignerdranch.expandablerecyclerview.model.Parent

data class FAQ(var question: String = "", var answer: String = ""): Parent<String> {
    override fun getChildList() = listOf(answer)

    override fun isInitiallyExpanded() = false
}