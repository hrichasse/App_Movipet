# 🐾 MoviPet - App de Transporte para Mascotas

## 📋 Descripción del Proyecto

**MoviPet** es una aplicación móvil Android desarrollada en Kotlin con Jetpack Compose que facilita el traslado seguro de mascotas desde el domicilio hasta la veterinaria. La app conecta a dueños de mascotas con conductores especializados, ofreciendo un servicio de transporte confiable y rastreable en tiempo real.

## 👥 Estudiantes

- **Hernan Richasse**
- **Donkan Marin**

## ✨ Funcionalidades Implementadas

### 🔐 Autenticación y Perfil
- Sistema de login y registro con validación de campos
- Validación de email y contraseñas seguras
- Pantalla de perfil de usuario con opciones de configuración
- Tema oscuro/claro persistente con DataStore Preferences

### 🐕 Gestión de Mascotas
- CRUD completo de mascotas con persistencia en Room Database
- Captura de fotos con CameraX para identificar mascotas
- Visualización de miniaturas de fotos con Coil
- Almacenamiento de imágenes en MediaStore

### 🚗 Flujo de Solicitud de Viaje
- Selección de ubicación con Google Maps y geocodificación inversa
- Selección de tipo de mascota y vehículo
- Confirmación de detalles del viaje
- Búsqueda automática de conductor
- Selección manual de conductor disponible
- Rastreo en tiempo real del conductor en ruta

### 📱 Recursos Nativos Android
1. **GPS y Mapas**: 
   - Google Maps SDK con Maps Compose
   - FusedLocationProviderClient para ubicación en tiempo real
   - Geocoder para convertir coordenadas a direcciones legibles

2. **Notificaciones Locales**:
   - Canal de notificaciones para estados del viaje
   - 4 tipos de notificaciones: conductor asignado, conductor cerca, viaje iniciado, viaje completado
   - NotificationManager con soporte para Android 13+

3. **Cámara**:
   - CameraX para captura de fotos
   - Preview en tiempo real con PreviewView
   - Guardado en MediaStore con permisos dinámicos
   - Asociación de fotos a mascotas específicas

4. **Persistencia**:
   - Room Database para mascotas y viajes
   - DataStore Preferences para configuración del tema
   - Repositorios con Flow para reactividad

### 💬 Comunicación y Valoración
- Chat en tiempo real con el conductor
- Sistema de valoración con estrellas (1-5)
- Guardado de comentarios y ratings en la base de datos
- Historial completo de viajes con detalles

### 🏥 Servicios Adicionales
- Lista de veterinarias asociadas
- Métodos de pago (tarjeta débito/crédito, efectivo)
- Comprobante de viaje con resumen detallado
- Historial de viajes con fechas y costos

### 🎨 UI/UX
- Interfaz moderna con Material Design 3
- Animaciones de navegación fluidas (slide + fade)
- Header reutilizable con logo MoviPet en todas las pantallas
- Botones de navegación optimizados (back y home)
- Validaciones separadas del UI siguiendo buenas prácticas
- Iconos en campos de formulario para mejor UX

## 🛠️ Tecnologías Utilizadas

### Core
- **Kotlin** 2.0.21
- **Jetpack Compose** BOM 2024.09.00
- **Material Design 3**
- **Android Gradle Plugin** 8.13.0

### Arquitectura
- **MVVM** (Model-View-ViewModel)
- **Navigation Compose** 2.9.5 con animaciones
- **Lifecycle** ViewModels y StateFlows
- **Kotlin Coroutines** para operaciones asíncronas

### Persistencia
- **Room** 2.6.1 con KSP para base de datos local
- **DataStore Preferences** 1.1.1 para configuración

### APIs y Recursos Nativos
- **Google Maps SDK** 19.0.0 + Maps Compose 4.4.1
- **Play Services Location** 21.3.0
- **CameraX** 1.4.0 (core, camera2, lifecycle, view, extensions)
- **Coil** 2.6.0 para carga de imágenes

### Build Tools
- **Gradle** 8.13
- **KSP** 2.0.21-1.0.28
- **Compile SDK** 36 / **Target SDK** 36 / **Min SDK** 24

## 📂 Estructura del Proyecto

```
app/src/main/java/com/example/uinavegacion/
├── data/
│   ├── dao/              # Data Access Objects (Room)
│   ├── entity/           # Entidades de base de datos
│   ├── repository/       # Repositorios para abstracción de datos
│   ├── MoviPetDatabase.kt
│   └── ThemePreferences.kt
├── domain/
│   └── validation/       # Lógica de validación de campos
├── navigation/
│   ├── NavGraph.kt       # Grafo de navegación
│   └── Routes.kt         # Definición de rutas
├── notification/
│   └── NotificationHelper.kt
├── ui/
│   ├── components/       # Componentes reutilizables (MoviPetHeader)
│   ├── screen/           # Pantallas de la app (20+ screens)
│   └── theme/            # Colores, tipografía, tema
├── viewmodel/            # ViewModels por pantalla
└── MainActivity.kt
```

## 🚀 Pasos para Ejecutar el Proyecto

### Prerrequisitos
1. **Android Studio** Hedgehog (2023.1.1) o superior
2. **JDK** 17 o superior
3. **Dispositivo Android** o **Emulador** con API 24+ (Android 7.0+)
4. **Google Maps API Key** (ya incluida en el proyecto)

### Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/hrichasse/App_Movipet.git
   cd App_Movipet
   ```

2. **Abrir en Android Studio**
   - File → Open
   - Seleccionar la carpeta `Movipet`
   - Esperar a que Gradle sincronice las dependencias

3. **Configurar el emulador** (opcional)
   - Tools → Device Manager → Create Device
   - Seleccionar un dispositivo con Play Store
   - Descargar System Image con API 34 o superior
   - En configuración del emulador:
     - Camera → Front: Webcam0
     - Camera → Rear: Webcam0 (para probar funcionalidad de cámara)

4. **Ejecutar la app**
   - Presionar **Run ▶️** o `Shift + F10`
   - Seleccionar el dispositivo/emulador
   - Esperar a que se instale y abra la app

### Compilar APK manualmente (opcional)
```bash
# Desde la raíz del proyecto
./gradlew.bat assembleDebug

# El APK estará en:
# app/build/outputs/apk/debug/app-debug.apk
```

## 📱 Uso de la Aplicación

1. **Registro/Login**: Crea una cuenta o inicia sesión
2. **Agregar Mascota**: Ve a "Mis mascotas" → Agregar → Toma foto (opcional)
3. **Solicitar Viaje**:
   - Desde el menú principal → "Solicitar viaje"
   - Selecciona ubicación de origen y destino en el mapa
   - Elige tipo de mascota y vehículo
   - Confirma detalles
   - Espera asignación de conductor
   - Rastrea en tiempo real
4. **Chat**: Comunícate con el conductor durante el viaje
5. **Valorar**: Al finalizar, valora el servicio y deja comentarios
6. **Historial**: Consulta tus viajes anteriores con detalles completos

## 🎯 Cumplimiento de Requisitos del Proyecto

- ✅ **Navegación entre pantallas** con Navigation Compose y animaciones
- ✅ **Validaciones separadas del UI** en paquete `domain/validation`
- ✅ **4 Recursos nativos Android**: GPS/Maps, Notificaciones, Cámara, Persistencia (Room + DataStore)
- ✅ **Persistencia con Room**: Entities, DAOs, Repositories con Flow
- ✅ **MVVM**: ViewModels separados, StateFlow/LiveData, repositorios
- ✅ **Material Design 3**: Componentes modernos, tema personalizado
- ✅ **Animaciones**: Transiciones de navegación con slide + fade
- ✅ **UX mejorada**: Iconos en formularios, feedback visual, headers consistentes

## 🐛 Solución de Problemas

### La app se cierra al abrir
- Desinstala versiones anteriores desde Configuración → Apps
- Limpia el proyecto: Build → Clean Project
- Reconstruye: Build → Rebuild Project

### El mapa no se muestra
- Verifica que la API Key de Google Maps esté configurada en `AndroidManifest.xml`
- Asegúrate de tener conexión a internet
- Revisa que los permisos de ubicación estén concedidos

### La cámara no funciona en el emulador
- Verifica la configuración del emulador (Tools → Device Manager → Edit)
- Asegura que Webcam0 esté configurado para Front y Rear camera
- Concede los permisos de cámara cuando la app lo solicite

### Error de compilación con Room
- File → Invalidate Caches → Invalidate and Restart
- Borra la carpeta `build` y vuelve a compilar

## 📄 Licencia

Este proyecto es un trabajo académico desarrollado para fines educativos.

## 📧 Contacto

Para consultas sobre el proyecto:
- **Hernan Richasse** 
- **Donkan Marin**

---

**MoviPet** - Transporte seguro para tus mascotas 🐾🚗
