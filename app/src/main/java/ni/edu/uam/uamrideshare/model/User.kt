package ni.edu.uam.uamrideshare.model

import androidx.compose.ui.graphics.Color

// Modelos de datos
enum class Role { DRIVER, PASSENGER }

data class UserStats(
    val ridesAsDriver: Int,
    val ridesAsPassenger: Int,
    val averageRating: Float
)

data class UserProfile(
    val username: String,
    val email: String,
    val cif: String,
    val roles: Set<Role>,
    val vehiclePlate: String? = null,
    val preferredPaymentMethod: String? = null,
    val stats: UserStats,
    val unreadNotifications: Int
)

// Paleta de colores
val Thistle = Color(0xFFCDB4DB)
val PastelPetal = Color(0xFFFFC8DD)
val BabyPink = Color(0xFFFFAFCC)
val IcyBlue = Color(0xFFBDE0FE)
val SkyBlue = Color(0xFFA2D2FF)