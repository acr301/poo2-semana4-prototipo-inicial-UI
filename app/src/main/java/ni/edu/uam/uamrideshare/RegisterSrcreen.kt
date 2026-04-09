package ni.edu.uam.uamrideshare

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
 * Actividad de Registro que utiliza el tema global del proyecto.
 */
class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Usamos UAMRideShareTheme para mantener la consistencia con el resto de la app
            UAMRideShareTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = IcyBlue // Fondo limpio usando la paleta oficial
                ) {
                    RegistroScreen()
                }
            }
        }
    }
}

@Composable
fun RegistroScreen() {
    // --- ESTADO (Variables reactivas para los campos de entrada) ---
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var carnet by remember { mutableStateOf("") }
    var contra by remember { mutableStateOf("") }
    var confirmarContra by remember { mutableStateOf("") }

    // Estado para manejar el feedback visual al usuario
    var mensajeFeedback by remember { mutableStateOf("") }
    
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 1. Logo UAM (Consistencia visual)
        Image(
            painter = painterResource(id = R.drawable.uamlogo),
            contentDescription = "Logo de la UAM",
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 16.dp)
        )

        Text(
            text = "Registro UAM RideShare",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- CAMPOS DE TEXTO (Usando OutlinedTextField para coincidir con el estilo de ProfileScreen) ---

        CustomRegisterField(
            value = nombre, 
            onValueChange = { nombre = it }, 
            label = "Nombre Completo"
        )
        
        Spacer(modifier = Modifier.height(12.dp))

        CustomRegisterField(
            value = correo, 
            onValueChange = { correo = it }, 
            label = "Correo Institucional (@uam.edu.ni)"
        )

        Spacer(modifier = Modifier.height(12.dp))

        CustomRegisterField(
            value = carnet, 
            onValueChange = { carnet = it }, 
            label = "Número de Carnet"
        )

        Spacer(modifier = Modifier.height(12.dp))

        CustomRegisterField(
            value = contra, 
            onValueChange = { contra = it }, 
            label = "Contraseña", 
            isPassword = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        CustomRegisterField(
            value = confirmarContra, 
            onValueChange = { confirmarContra = it }, 
            label = "Confirmar Contraseña", 
            isPassword = true
        )

        Spacer(modifier = Modifier.height(30.dp))

        // --- BOTÓN REGISTRAR (Estilo alineado con ProfileScreen: SkyBlue con texto negro) ---
        Button(
            onClick = {
                // Validación básica de campos
                if (nombre.isEmpty() || correo.isEmpty() || carnet.isEmpty() || contra.isEmpty()) {
                    mensajeFeedback = "⚠️ Error: Todos los campos son obligatorios."
                } else if (!correo.endsWith("@uam.edu.ni")) {
                    mensajeFeedback = "⚠️ Error: Solo correos @uam.edu.ni permitidos."
                } else if (contra != confirmarContra) {
                    mensajeFeedback = "⚠️ Error: Las contraseñas no coinciden."
                } else {
                    mensajeFeedback = "✅ Registro simulado exitoso"
                    Toast.makeText(context, "¡Bienvenido a UAM RideShare!", Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SkyBlue),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Registrarse", fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        }

        // --- FEEDBACK VISUAL ---
        if (mensajeFeedback.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = mensajeFeedback,
                color = if (mensajeFeedback.contains("✅")) Color(0xFF2E7D32) else Color.Red,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Enlace para ir al login (estilizado)
        TextButton(onClick = { /* Lógica de navegación */ }) {
            Text(
                text = "¿Ya tienes cuenta? Inicia sesión",
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * Campo de texto personalizado para el registro.
 * Mantiene la consistencia visual utilizando OutlinedTextField y colores de la paleta.
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
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BabyPink, // Color de acento de la paleta
            unfocusedBorderColor = Color.LightGray,
            focusedLabelColor = BabyPink,
            focusedContainerColor = Color.White.copy(alpha = 0.5f),
            unfocusedContainerColor = Color.White.copy(alpha = 0.5f)
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
