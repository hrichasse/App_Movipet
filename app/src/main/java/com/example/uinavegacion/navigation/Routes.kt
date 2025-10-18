package com.example.uinavegacion.navigation

// Clase sellada para rutas: evita "strings mágicos" y facilita refactors
sealed class Route(val path: String) { // Cada objeto representa una pantalla
    data object Login : Route("login") // Pantalla de inicio de sesión
    data object UserMenu : Route("user_menu") // Menú de usuario/perfil
    data object LocationSelection : Route("location_selection") // Selección de ubicación
    data object PetTypeSelection : Route("pet_type_selection") // Selección de tipo de mascota
    data object VehicleTypeSelection : Route("vehicle_type_selection") // Selección de tipo de vehículo
    data object Confirmation : Route("confirmation") // Confirmación de detalles
    data object DriverSearch : Route("driver_search") // Búsqueda de conductor
    data object DriverSelection : Route("driver_selection") // Selección de conductor
    data object DriverEnRoute : Route("driver_en_route") // Conductor en camino
}

/*
* “Strings mágicos” se refiere a cuando pones un texto duro y repetido en varias partes del código,
* Si mañana cambias "home" por "inicio", tendrías que buscar todas las ocurrencias de "home" a mano.
* Eso es frágil y propenso a errores.
La idea es: mejor centralizar esos strings en una sola clase (Route), y usarlos desde ahí.*/