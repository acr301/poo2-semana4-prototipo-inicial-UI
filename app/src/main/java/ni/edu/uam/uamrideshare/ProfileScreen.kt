package ni.edu.uam.uamrideshare

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ni.edu.uam.uamrideshare.model.Role
import ni.edu.uam.uamrideshare.model.UserProfile
import ni.edu.uam.uamrideshare.model.UserStats
import ni.edu.uam.uamrideshare.ui.theme.*

/**
 * Pantalla principal de Perfil del Estudiante.
 * Utiliza un Scaffold para la estructura base y una LazyColumn para el contenido scrolleable.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentProfileScreen() {
    // Datos de prueba (Mock Data) para representar a un usuario en la UI
    val mockUser = UserProfile(
        username = "juan.perez",
        email = "juan.perez@universidad.edu",
        cif = "20241234",
        roles = setOf(Role.DRIVER, Role.PASSENGER),
        vehiclePlate = "ABC-1234",
        preferredPaymentMethod = "Tarjeta",
        stats = UserStats(12, 8, 4.7f),
        unreadNotifications = 3
    )

    Scaffold(
        containerColor = porcelain, // Fondo general de la pantalla (Paleta: Porcelain)
        topBar = {
            // Barra superior con el título y contador de notificaciones
            CenterAlignedTopAppBar(
                title = { Text("Mi Perfil", fontWeight = FontWeight.Bold) },
                actions = {
                    BadgedBox(
                        badge = {
                            // Badge con color Stormy Teal según paleta
                            Badge(containerColor = stormyteal) {
                                Text(mockUser.unreadNotifications.toString(), color = Color.White)
                            }
                        },
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notificaciones")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent // Permite ver el Porcelain del Scaffold
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Color.White), // Cuerpo principal en blanco para contraste
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(24.dp)
        ) {
            // 1. Cabecera con Avatar (Usa Pearlaqua para las iniciales)
            item {
                ProfileAvatar(username = mockUser.username)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "@${mockUser.username}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = mockUser.email, color = Color.Gray)
                Spacer(modifier = Modifier.height(24.dp))
            }

            // 2. Sección de Estadísticas con fondo Pearlaqua suave
            item {
                StatsSection(mockUser.stats)
                Spacer(modifier = Modifier.height(24.dp))
            }

            // 3. Tarjeta de Información Estudiantil
            item {
                InfoCard(title = "Información Estudiantil", icon = Icons.Default.Person) {
                    InfoRow(label = "CIF", value = mockUser.cif)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // 4. Configuración de Roles (Usa Tropicalteal para elementos secundarios como el rol Pasajero)
            item {
                InfoCard(title = "Roles y Configuración", icon = Icons.Default.Settings) {
                    if (mockUser.roles.contains(Role.DRIVER)) {
                        RoleDetailItem(
                            roleName = "CONDUCTOR",
                            label = "Placa del Vehículo",
                            value = mockUser.vehiclePlate ?: "No registrada",
                            color = teal // Color teal para conductor
                        )
                    }
                    if (mockUser.roles.contains(Role.PASSENGER)) {
                        if (mockUser.roles.contains(Role.DRIVER)) Spacer(modifier = Modifier.height(12.dp))
                        RoleDetailItem(
                            roleName = "PASAJERO",
                            label = "Método de Pago",
                            value = mockUser.preferredPaymentMethod ?: "Efectivo",
                            color = tropicalteal // Color tropicalteal para pasajero (secundario)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            // 5. Botones de Acción (Paleta: Teal para el principal)
            item {
                Button(
                    onClick = { /* Lógica editar */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = teal),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null, tint = Color.White)
                    Spacer(Modifier.width(8.dp))
                    Text("Editar Perfil", color = Color.White)
                }

                TextButton(
                    onClick = { /* Lógica logout */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cerrar Sesión", color = Color.Red.copy(alpha = 0.7f))
                }
            }
        }
    }
}

/**
 * Componente circular para mostrar las iniciales del usuario.
 */
@Composable
fun ProfileAvatar(username: String) {
    val initials = username.take(2).uppercase()
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(pearlaqua),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

/**
 * Muestra métricas rápidas del usuario con un diseño horizontal.
 */
@Composable
fun StatsSection(stats: UserStats) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(pearlaqua.copy(alpha = 0.2f)) // Fondo Pearlaqua suave
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatItem(label = "Conductor", value = stats.ridesAsDriver.toString())
        StatItem(label = "Pasajero", value = stats.ridesAsPassenger.toString())
        StatItem(label = "Rating", value = "${stats.averageRating} ★")
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(text = label, fontSize = 12.sp, color = Color.DarkGray)
    }
}

/**
 * Tarjeta genérica con icono y título para agrupar información.
 */
@Composable
fun InfoCard(title: String, icon: ImageVector, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = teal, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
            Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        }
        Card(
            modifier = Modifier.padding(top = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, pearlaqua)
        ) {
            Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                content()
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = label, color = Color.Gray)
        Text(text = value, fontWeight = FontWeight.Medium)
    }
}

/**
 * Campo de texto personalizado que usa la paleta de colores cuando está enfocado.
 */
@Composable
fun RoleDetailItem(roleName: String, label: String, value: String, color: Color) {
    var textValue by remember { mutableStateOf(value) }

    Column {
        Surface(
            color = color,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(
                text = roleName,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        OutlinedTextField(
            value = textValue,
            onValueChange = { textValue = it },
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(fontSize = 14.sp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = stormyteal, // Color Stormy Teal al enfocar
                unfocusedBorderColor = Color.LightGray,
                focusedLabelColor = stormyteal
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StudentProfileScreenPreview() {
    StudentProfileScreen()
}
