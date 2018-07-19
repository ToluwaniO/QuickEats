package toluog.quickeats

import android.arch.lifecycle.ViewModel
import toluog.quickeats.model.Card
import toluog.quickeats.network.Repository

class UserViewModel: ViewModel() {
    private val repo = Repository(Repository.RepoType.CARD)

    fun getCards() = repo.get()

    fun addCard(card: Card) = FirebaseManager.addCard(card)

}