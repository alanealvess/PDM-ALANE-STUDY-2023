package pdm.study

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import pdm.study.db.FirebaseDB
import pdm.study.model.User

class MainViewModel : ViewModel() {
    private var _user = mutableStateOf(User("...", "..."))

    var user : User
        get() = _user.value
        set(value) {
            _user.value = value
        }

    init {
        FirebaseDB.onUserLogin = {
            user = it
        }
        FirebaseDB.onUserLogout = {
            user.name = "..."
        }
    }
}