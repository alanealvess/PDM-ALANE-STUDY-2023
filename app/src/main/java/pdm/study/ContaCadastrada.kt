package pdm.study

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pdm.study.ui.theme.Pessego
import pdm.study.ui.theme.StudyTheme

class ContaCadastrada : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContaCadastradaMsg(this,
                        nome = intent.getStringExtra("nome") ?: "",
                        email = intent.getStringExtra("email") ?: ""
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContaCadastradaMsg(activity: ComponentActivity, nome: String, email: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Criar uma conta",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = "Conta cadastrada com sucesso !!!",
            style = TextStyle(
                fontSize = 16.sp
            )
        )
        Spacer(modifier = Modifier.size(12.dp))
        Image(
            painter = painterResource(id = R.drawable.conta_cadastrada),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .padding(16.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.size(24.dp))
        Text(
            text = "Nome: $nome",
            style = TextStyle(
                fontSize = 16.sp
            )
        )
        Spacer(modifier = Modifier.size(24.dp))
        Text(
            text = "Email: $email",
            style = TextStyle(
                fontSize = 16.sp
            )
        )
        Button(
            onClick = {
                val intent = Intent(activity, ContaEntrar::class.java).apply {
                    putExtra("nome", nome)
                    putExtra("email", email)
                    flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                }
                activity.startActivity(intent)
            },
            modifier = Modifier.padding(16.dp),
            colors = ButtonDefaults.buttonColors(Pessego)
        ) {
            Text(
                text = "Entrar",
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
            )
        }
    }
}

@Composable
fun ContaCadastradaPreview() {
    val activity = ContaCadastrar()
    StudyTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ContaCadastradaMsg(
                activity,
                nome = "Alane alves",
                email = "alane@gmail.com"
            )
        }
    }
}

@Preview
@Composable
fun ContaCadastradaPreviewWrapper() {
    ContaCadastradaPreview()
}