# Configuración de Google Maps API Key para MoviPet

## Pasos para obtener tu API Key

### 1. Ir a Google Cloud Console
- Visita: https://console.cloud.google.com/

### 2. Crear un nuevo proyecto (si no tienes uno)
- Haz clic en el selector de proyectos en la parte superior
- Click en "Nuevo proyecto"
- Dale un nombre: "MoviPet" o el que prefieras
- Haz clic en "Crear"

### 3. Habilitar las APIs necesarias
- En el menú lateral, ve a **APIs y servicios** → **Biblioteca**
- Busca y habilita las siguientes APIs:
  - **Maps SDK for Android**
  - **Geocoding API** (opcional, para convertir coordenadas a direcciones)
  - **Places API** (opcional, para búsqueda de lugares)

### 4. Crear credenciales (API Key)
- Ve a **APIs y servicios** → **Credenciales**
- Haz clic en **+ Crear credenciales** → **Clave de API**
- Se generará tu API Key automáticamente

### 5. Restringir la API Key (Recomendado)
Para mayor seguridad:

#### Restricción por aplicación Android:
- Haz clic en tu API Key recién creada
- En "Restricciones de aplicaciones", selecciona **Aplicaciones para Android**
- Haz clic en **+ Agregar nombre de paquete y huella digital**
- Nombre del paquete: `com.movipet.app`
- Para obtener la huella SHA-1 de debug, ejecuta en PowerShell:
  ```powershell
  cd C:\Users\TU_USUARIO\.android
  keytool -list -v -keystore debug.keystore -alias androiddebugkey -storepass android -keypass android
  ```
- Copia la huella SHA-1 que aparece
- Pégala en el campo "Huella digital del certificado SHA-1"

#### Restricción por API:
- En "Restricciones de API", selecciona **Restringir clave**
- Marca solo las APIs que habilitaste:
  - Maps SDK for Android
  - Geocoding API (si la habilitaste)
  - Places API (si la habilitaste)
- Guarda los cambios

### 6. Copiar tu API Key
- Copia tu API Key generada (tiene formato: `AIzaSy...`)

### 7. Agregar la API Key al proyecto

Abre el archivo `app/src/main/AndroidManifest.xml` y reemplaza `YOUR_GOOGLE_MAPS_API_KEY` con tu clave:

```xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="AIzaSyXXXXXXXXXXXXXXXXXXXXXXXXXX"/>
```

### 8. Compilar y ejecutar
```powershell
.\gradlew.bat assembleDebug
```

## Funcionalidades GPS integradas

### Pantallas con GPS:
1. **LocationSelectionScreen** - Muestra tu ubicación actual en el mapa
2. **DriverEnRouteScreen** - Seguimiento en tiempo real del viaje

### Permisos
La app pedirá automáticamente los permisos de ubicación:
- `ACCESS_FINE_LOCATION` - Ubicación precisa (GPS)
- `ACCESS_COARSE_LOCATION` - Ubicación aproximada (redes WiFi/celular)

El usuario puede conceder uno u otro, o ambos.

### Comportamiento
- **Con permisos concedidos**: El mapa mostrará la ubicación actual con un marcador
- **Sin permisos**: Se mostrará un mensaje pidiendo activar la ubicación
- **GPS actualizado cada 2 segundos** mientras estés en las pantallas

## Solución de problemas

### El mapa no se muestra
- Verifica que copiaste correctamente la API Key
- Asegúrate de haber habilitado "Maps SDK for Android"
- Revisa que la restricción SHA-1 coincida con tu keystore de debug

### La ubicación no se actualiza
- Verifica que concediste los permisos de ubicación
- Asegúrate de que el GPS del dispositivo está activado
- En emulador: usa las herramientas de Android Studio para simular ubicación

### Error de compilación
- Verifica que todas las dependencias estén en `app/build.gradle.kts`
- Ejecuta: `.\gradlew.bat clean build`

## Próximas mejoras sugeridas

1. **Geocoding inverso**: Convertir lat/lon a direcciones legibles
2. **Autocompletado de lugares**: Integrar Places Autocomplete
3. **Cálculo de rutas**: Usar Directions API para mostrar la ruta
4. **Estimación de tiempo**: Calcular ETA con Distance Matrix API
5. **Marcadores del conductor**: Mostrar posición del conductor en tiempo real

---

**Nota importante**: 
- La API Key tiene cuota gratuita mensual: 28,500 cargas de mapa gratis/mes
- Monitorea tu uso en Google Cloud Console
- Para producción, agrega facturación y restricciones más estrictas
