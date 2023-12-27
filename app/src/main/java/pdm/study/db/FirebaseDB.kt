package pdm.study.db

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import pdm.study.model.User

object FirebaseDB {
    private val db = Firebase.firestore
    private val auth = Firebase.auth
    var onUserLogin : ((User) -> Unit)? = null
    var onUserLogout : (() -> Unit)? = null
    init {
        auth.addAuthStateListener { auth ->
            if (auth.currentUser != null) {
                val refCurrUser = db.collection("users")
                    .document(auth.currentUser!!.uid)
                refCurrUser.get()
                    .addOnSuccessListener {
                        it.toObject(User::class.java)?.let {
                            onUserLogin?.invoke(it) } }

            } else {
                onUserLogout?.invoke()
            }
        }
    }
    fun register(userName: String, email: String) {
        val tmpUser = User(userName, email)
        db.collection("users").
        document(auth.currentUser?.uid + "").set(tmpUser);
    }
}