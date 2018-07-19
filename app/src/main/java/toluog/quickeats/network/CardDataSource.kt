package toluog.quickeats.network

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import toluog.quickeats.model.Card

class CardDataSource(uid: String): DataSource<Card>() {

    private val TAG = CardDataSource::class.java.simpleName
    private val docRef: DocumentReference = db.collection("users").document(uid)
    private val liveCards = MutableLiveData<List<Card>>()

    init {
        initData()
    }

    private fun initData() {
        docRef.addSnapshotListener { documentSnapshot, e ->
            if(e != null) {
                Log.d(TAG, e.toString())
                return@addSnapshotListener
            }
            val card = documentSnapshot?.toObject(Card::class.java)
            if(card != null) {
                dataList.add(card)
            }
            liveData.postValue(dataList)
        }
    }

}