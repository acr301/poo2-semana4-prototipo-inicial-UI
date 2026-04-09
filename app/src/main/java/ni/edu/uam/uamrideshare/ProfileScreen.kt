package ni.edu.uam.uamrideshare

import androidx.compose.animation.core.copy
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentProfileScreen() {
    // Mock Data
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
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mi Perfil", fontWeight = FontWeight.Bold) },
                actions = {
                    BadgedBox(
                        badge = {
                            Badge(containerColor = BabyPink) {
                                Text(mockUser.unreadNotifications.toString(), color = Color.White)
                            }
                        },
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notificaciones")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = IcyBlue)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(16.dp)
        ) {
            // 1. Avatar Seccion
            item {
                ProfileAvatar(username = mockUser.username)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "@${mockUser.username}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = mockUser.email, color = Color.Gray)
                Spacer(modifier = Modifier.height(24.dp))
            }

            // 2. Estadísticas
            item {
                StatsSection(mockUser.stats)
                Spacer(modifier = Modifier.height(24.dp))
            }

            // 3. Información Personal (CIF)
            item {
                InfoCard(title = "Información Estudiantil", icon = Icons.Default.School) {
                    InfoRow(label = "CIF", value = mockUser.cif)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // 4. Roles y Detalles
            item {
                InfoCard(title = "Roles y Configuración", icon = Icons.Default.Settings) {
                    if (mockUser.roles.contains(Role.DRIVER)) {
                        RoleDetailItem(
                            roleName = "CONDUCTOR",
                            label = "Placa del Vehículo",
                            value = mockUser.vehiclePlate ?: "No registrada",
                            color = SkyBlue
                        )
                    }
                    if (mockUser.roles.contains(Role.PASSENGER)) {
                        Spacer(modifier = Modifier.height(12.dp))
                        RoleDetailItem(
                            roleName = "PASAJERO",
                            label = "Método de Pago",
                            value = mockUser.preferredPaymentMethod ?: "Efectivo",
                            color = Thistle
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            // 5. Botones de Acción
            item {
                Button(
                    onClick = { /* Lógica editar */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = SkyBlue),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Editar Perfil", color = Color.Black)
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

@Composable
fun ProfileAvatar(username: String) {
    val initials = username.take(2).uppercase()
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(PastelPetal),
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

@Composable
fun StatsSection(stats: UserStats) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(IcyBlue.copy(alpha = 0.4f))
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

@Composable
fun InfoCard(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = SkyBlue, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
            Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        }
        Card(
            modifier = Modifier.padding(top = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, IcyBlue)
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
                color = Color.DarkGray
            )
        }
        OutlinedTextField(
            value = textValue,
            onValueChange = { textValue = it },
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = color,
                unfocusedBorderColor = Color.LightGray
            )
        )
    }
}