package ni.edu.uam.uamrideshare

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ni.edu.uam.uamrideshare.ui.theme.*

/**
 * Pantalla de Registro
 */
class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UAMRideShareTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = porcelain // Fondo base de la paleta
                ) {
                    RegistroScreen()
                }
            }
        }
    }
}

@Composable
fun RegistroScreen() {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var carnet by remember { mutableStateOf("") }
    var contra by remember { mutableStateOf("") }
    var confirmarContra by remember { mutableStateOf("") }
    var mensajeFeedback by remember { mutableStateOf("") }
    
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- CABECERA ---
        Spacer(modifier = Modifier.height(60.dp))
        
        // Imagen del logo
        Image(
            painter = painterResource(id = R.drawable.uamlogo),
            contentDescription = "Logo UAM",
            modifier = Modifier.size(140.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "UAM RideShare",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black
        )
        Text(
            text = "Crea tu cuenta universitaria",
            fontSize = 14.sp,
            color = stormyteal.copy(alpha = 0.8f) // Uso de stormyteal para sutileza
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- FORMULARIO ---
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White,
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Datos de Registro",
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(20.dp))

                CustomRegisterField(nombre, { nombre = it }, "Nombre Completo")
                Spacer(modifier = Modifier.height(12.dp))
                
                CustomRegisterField(correo, { correo = it }, "Correo Institucional")
                Spacer(modifier = Modifier.height(12.dp))
                
                CustomRegisterField(carnet, { carnet = it }, "Número de Carnet")
                Spacer(modifier = Modifier.height(12.dp))
                
                CustomRegisterField(contra, { contra = it }, "Contraseña", isPassword = true)
                Spacer(modifier = Modifier.height(12.dp))
                
                CustomRegisterField(confirmarContra, { confirmarContra = it }, "Confirmar Contraseña", isPassword = true)

                // Feedback visual
                if (mensajeFeedback.isNotEmpty()) {
                    Text(
                        text = mensajeFeedback,
                        color = if (mensajeFeedback.contains("✅")) Color(0xFF388E3C) else Color.Red,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(top = 16.dp),
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Botón de Registro con color 'teal' (Acento principal)
                Button(
                    onClick = {
                        if (nombre.isEmpty() || correo.isEmpty() || carnet.isEmpty() || contra.isEmpty()) {
                            mensajeFeedback = "⚠️ Todos los campos son obligatorios"
                        } else if (!correo.endsWith("@uam.edu.ni")) {
                            mensajeFeedback = "⚠️ Usa tu correo @uam.edu.ni"
                        } else if (contra != confirmarContra) {
                            mensajeFeedback = "⚠️ Las contraseñas no coinciden"
                        } else {
                            mensajeFeedback = "✅ ¡Registro exitoso!"
                            Toast.makeText(context, "Bienvenido a UAM RideShare", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = teal),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("REGISTRARME", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Link para Iniciar Sesión con 'stormyteal'
                TextButton(onClick = { /* Lógica de navegación */ }) {
                    Row {
                        Text("¿Ya tienes cuenta? ", color = Color.Gray)
                        Text("Inicia Sesión", color = stormyteal, fontWeight = FontWeight.Bold)
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

/**
 * Campo de texto personalizado adaptado a la nueva paleta.
 */
@Composable
fun CustomRegisterField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 14.sp) },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = teal,
            unfocusedBorderColor = pearlaqua,
            focusedLabelColor = teal,
            focusedContainerColor = tropicalteal.copy(alpha = 0.05f),
            unfocusedContainerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun RegistroScreenPreview() {
    UAMRideShareTheme {
        RegistroScreen()
    }
}
