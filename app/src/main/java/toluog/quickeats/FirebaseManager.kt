package toluog.quickeats

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.firebase.ui.auth.AuthUI
import android.support.v4.app.ActivityCompat.startActivityForResult
import com.google.firebase.firestore.FirebaseFirestore
import toluog.quickeats.model.Card
import toluog.quickeats.model.User
import java.util.*


class FirebaseManager {
    companion object {
        val auth = FirebaseAuth.getInstance()
        private val RC_SIGN_IN = 8697

        @JvmStatic
        fun isSignedIn() = auth.currentUser != null

        @JvmStatic
        fun user(): User {
            val fUser = auth.currentUser
            return User().apply {
                uid = fUser?.uid ?: ""
                email = fUser?.email ?: ""
                displayName = fUser?.displayName ?: ""
            }
        }

        fun signOut() = auth.signOut()

        fun addCard(card: Card) {
            val fStore = FirebaseFirestore.getInstance()
            fStore.collection("users").document(user().uid).set(card)
        }

        @JvmStatic
        fun startSignIn(activity: Activity) {
            val providers = Arrays.asList(
                    AuthUI.IdpConfig.EmailBuilder().build(),
                    AuthUI.IdpConfig.GoogleBuilder().build())

// Create and launch sign-in intent
            activity.startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setTheme(R.style.GreenTheme)
                            .setLogo(R.drawable.ic_restaurant_service)
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN)
        }
    }
}