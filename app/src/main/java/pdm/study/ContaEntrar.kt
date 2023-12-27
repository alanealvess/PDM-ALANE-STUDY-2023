package pdm.study

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import pdm.study.ui.theme.Branco
import pdm.study.ui.theme.Pessego
import pdm.study.ui.theme.StudyTheme

class ContaEntrar : ComponentActivity() {
    private lateinit var fbAuthList: FBAuthListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.fbAuthList = FBAuthListener(this)

        setContent {
            StudyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContaLogin(this,
                        parEmail = intent.getStringExtra("email") ?: "")
                }
            }
        }
    }
    override fun onStart() {
        super.onStart()
        Firebase.auth.addAuthStateListener(fbAuthList)
    }
    override fun onStop() {
        super.onStop()
        Firebase.auth.removeAuthStateListener(fbAuthList)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContaLogin(activity: ComponentActivity, parEmail: String) {
    var email by remember { mutableStateOf(parEmail) }
    var password by remember { mutableStateOf("") }

    // Verifique se todos os campos estão preenchidos e se as senhas coincidem
    val isButtonEnabled = email.isNotBlank() && password.isNotBlank()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Entrar",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = "Bem-Vindo!",
            style = TextStyle(
                fontSize = 16.sp
            )
        )
        Spacer(modifier = Modifier.size(12.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { newEmail : String -> email = newEmail },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Pessego,
                unfocusedBorderColor = Color.Black
            )
        )
        OutlinedTextField(
            value = password,
            onValueChange = { newPassword : String -> password = newPassword },
            label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Pessego,
                unfocusedBorderColor = Color.Black
            )
        )
        Spacer(modifier = Modifier.size(24.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    Firebase.auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity!!) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(activity, "Login OK!", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(activity, "Login FALHOU!", Toast.LENGTH_LONG).show()
                            }
                        }
                },
                enabled = isButtonEnabled,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.7f),
                colors = ButtonDefaults.buttonColors(Pessego)
            ) {
                Text("Entrar",
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                )
            }
            Button(
                onClick = {
                    // Iniciar a atividade de redefinição de senha
                    activity.startActivity(
                        Intent(activity, RedefinirSenhaActivity::class.java).setFlags(
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
                        )
                    )
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.7f),
                colors = ButtonDefaults.buttonColors(Branco)
            ) {
                Text("Redefinir Senha",
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                )
            }
        }
    }
}

@Composable
fun ContaEntrarPreview() {
    val activity = ContaEntrar()
    StudyTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ContaLogin(activity, parEmail = "alanealves@gmail.com")
        }
    }
}

@Preview
@Composable
fun ContaEntrarPreviewWrapper() {
    ContaEntrarPreview()
}
