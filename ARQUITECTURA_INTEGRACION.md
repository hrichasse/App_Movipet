# Arquitectura de Integración - MoviPet

## 📐 Diagrama de Capas

```
┌─────────────────────────────────────────────────────────────────┐
│                        UI LAYER (Compose)                       │
│  LoginScreen │ PetsScreen │ RatingScreen │ TripReceiptScreen    │
│                   TravelHistoryScreen                           │
└────────────────────────────┬────────────────────────────────────┘
                             │ collectAsStateWithLifecycle()
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                      VIEWMODEL LAYER                            │
│  AuthViewModel │ RemotePetViewModel │ TripViewModel │           │
│                   ClinicViewModel                               │
│  Estados: loading, error, data (StateFlow)                      │
└────────────────────────────┬────────────────────────────────────┘
                             │ suspend fun / Result<T>
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                     REPOSITORY LAYER                            │
│  RemoteAuthRepository │ RemotePetRepository │                   │
│  RemoteTripRepository │ RemoteClinicRepository                  │
│  Manejo de errores, conversión Response → Result               │
└────────────────────────────┬────────────────────────────────────┘
                             │ Retrofit suspend calls
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                      API CLIENT LAYER                           │
│                    RetrofitClient (Singleton)                   │
│  Base URL: http://10.0.2.2:8080/ (emulador)                    │
│  Gson Converter │ OkHttpClient (30s timeout)                    │
└────────────────────────────┬────────────────────────────────────┘
                             │ HTTP REST API
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                    MovipetApi (Interface)                       │
│  @POST /api/auth/register    │  @POST /api/auth/login           │
│  @GET  /api/pets             │  @POST /api/pets                 │
│  @GET  /api/clinics          │  @POST /api/clinics              │
│  @GET  /api/trips            │  @POST /api/trips                │
└────────────────────────────┬────────────────────────────────────┘
                             │ JSON over HTTP
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                    BACKEND (Spring Boot)                        │
│  Controllers: AuthController, PetController,                    │
│               ClinicController, TripController                  │
│  Port: 8080 │ CORS: enabled for all origins                    │
└────────────────────────────┬────────────────────────────────────┘
                             │ MongoDB Driver
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                      MongoDB Database                           │
│  Collections: users │ pets │ clinics │ trips                    │
│  IDs: String (ObjectId) │ Timestamps: Long (millis)            │
└─────────────────────────────────────────────────────────────────┘
```

---

## 🔄 Flujo de Datos Típico

### Ejemplo: Crear Mascota

```
Usuario escribe "Luna" en PetsScreen
        ↓
PetsScreen.onClick → remoteVm.createPet(Pet(...))
        ↓
RemotePetViewModel.createPet(pet)
    ├─ _isLoading.value = true
    └─ viewModelScope.launch
        ↓
RemotePetRepository.createPet(pet)
    └─ withContext(Dispatchers.IO)
        ↓
RetrofitClient.movipetApi.createPet(pet)
        ↓
    POST http://10.0.2.2:8080/api/pets
    Headers: Content-Type: application/json
    Body: {"name":"Luna","type":"Perro","breed":"","age":0,"weight":0.0}
        ↓
Backend recibe, valida, inserta en MongoDB
        ↓
    Response 200 OK
    Body: {"id":"673abc123","name":"Luna","type":"Perro",...,"createdAt":1732911234567}
        ↓
Repository retorna Result.success(pet)
        ↓
ViewModel actualiza _pets.value + _isLoading.value = false
        ↓
PetsScreen recompone (via collectAsStateWithLifecycle)
        ↓
Usuario ve "Luna" en la lista ✅
```

---

## 🏗️ Patrones Implementados

### 1. **Repository Pattern**
- Abstrae la fuente de datos (backend) del ViewModel
- Permite cambiar implementación (mock, Room cache, etc.) sin tocar ViewModels
- Maneja conversiones `Response<T>` → `Result<T>`

### 2. **MVVM (Model-View-ViewModel)**
- **View (Composables)**: Solo renderiza UI basada en estados
- **ViewModel**: Lógica de negocio, maneja estados (loading/error/data)
- **Model**: Data classes (Pet, Trip, User, Clinic)

### 3. **Single Source of Truth**
- ViewModel expone `StateFlow<T>` como única fuente de verdad
- UI reacciona automáticamente a cambios vía `collectAsStateWithLifecycle()`

### 4. **Unidirectional Data Flow**
```
UI Event → ViewModel → Repository → Backend
                ↓
Backend Response → Repository → ViewModel → StateFlow
                ↓
StateFlow Update → Recompose UI
```

### 5. **Error Handling**
```kotlin
// En Repository
try {
    val response = api.createTrip(trip)
    if (response.isSuccessful && response.body() != null) {
        Result.success(response.body()!!)
    } else {
        Result.failure(Exception("Error ${response.code()}"))
    }
} catch (e: Exception) {
    Result.failure(e)
}

// En ViewModel
result.onSuccess { data -> _data.value = data }
      .onFailure { e -> _error.value = e.message }

// En UI
if (error != null) {
    Text("Error: $error", color = Color.Red)
}
```

---

## 📊 Estados del ViewModel

### AuthViewModel
```kotlin
_currentUser: StateFlow<User?>       // null = no logueado
_isLoading: StateFlow<Boolean>       // true durante login/register
_error: StateFlow<String?>           // mensaje de error o null
```

### RemotePetViewModel
```kotlin
_pets: StateFlow<List<Pet>>          // lista de mascotas
_isLoading: StateFlow<Boolean>       // true durante GET/POST
_error: StateFlow<String?>           // error de red o validación
```

### TripViewModel
```kotlin
_trips: StateFlow<List<Trip>>        // histórico de viajes
_lastCreatedTrip: StateFlow<Trip?>   // último viaje para recibo
_createSuccess: StateFlow<Boolean>   // true tras POST exitoso
_isLoading: StateFlow<Boolean>
_error: StateFlow<String?>
```

---

## 🔐 Seguridad (Actual vs Futuro)

### Implementación Actual
- ❌ **Sin JWT**: No hay token de autenticación
- ✅ **CORS habilitado**: Backend acepta cualquier origen
- ⚠️ Todos los endpoints abiertos (solo para desarrollo)

### Mejoras Futuras
1. **JWT Token**:
   ```kotlin
   // Login response incluye token
   data class LoginResponse(
       val user: User,
       val token: String
   )
   
   // Guardar en DataStore
   dataStore.edit { prefs ->
       prefs[TOKEN_KEY] = token
   }
   
   // Interceptor que agrega header
   class AuthInterceptor(private val dataStore: DataStore) : Interceptor {
       override fun intercept(chain: Chain): Response {
           val token = runBlocking { dataStore.data.first()[TOKEN_KEY] }
           val request = chain.request().newBuilder()
               .addHeader("Authorization", "Bearer $token")
               .build()
           return chain.proceed(request)
       }
   }
   ```

2. **Refresh Token**: Renovar token expirado automáticamente

3. **CORS Restrictivo**: Backend solo acepta app://movipet.com

---

## 🧪 Testing Strategy

### Unit Tests (ViewModels)
```kotlin
@Test
fun `createPet success updates pets list`() = runTest {
    val viewModel = RemotePetViewModel()
    val pet = Pet(name = "Test", type = "Perro", ...)
    
    viewModel.createPet(pet)
    advanceUntilIdle()
    
    assertTrue(viewModel.pets.value.contains(pet))
    assertFalse(viewModel.isLoading.value)
}
```

### Integration Tests (Repositories)
```kotlin
@Test
fun `RemotePetRepository returns pets from backend`() = runTest {
    val repository = RemotePetRepository(mockApi)
    val result = repository.getAllPets()
    
    assertTrue(result.isSuccess)
    assertEquals(3, result.getOrNull()?.size)
}
```

### UI Tests (Compose)
```kotlin
@Test
fun loginScreen_displaysErrorOnFailure() {
    composeTestRule.setContent {
        LoginScreen(navController, authViewModel)
    }
    
    authViewModel.login("wrong@email.com", "badpass")
    
    composeTestRule.onNodeWithText("Error:")
        .assertIsDisplayed()
}
```

---

## 📈 Métricas de Performance

### Timeouts Configurados
- **Connect**: 30s (default OkHttp)
- **Read**: 30s
- **Write**: 30s

### Tamaño de Respuestas Típicas
- Login: ~150 bytes
- Lista 50 mascotas: ~5 KB
- Lista 100 viajes: ~25 KB

### Latencias Esperadas (localhost)
- POST /api/auth/login: 50-200ms
- GET /api/pets: 30-100ms
- POST /api/trips: 80-250ms

---

## 🔄 Sincronización Room ↔ Backend (Futuro)

### Estrategia Híbrida
```kotlin
class HybridPetRepository(
    private val remoteRepo: RemotePetRepository,
    private val localDao: PetDao
) {
    suspend fun getPets(): Flow<List<Pet>> = flow {
        // 1. Emitir datos locales inmediatamente
        emit(localDao.getAllPets().first())
        
        // 2. Intentar actualizar desde backend
        remoteRepo.getAllPets().onSuccess { remotePets ->
            // 3. Guardar en Room
            localDao.deleteAll()
            localDao.insertAll(remotePets.map { it.toEntity() })
            
            // 4. Emitir datos actualizados
            emit(remotePets)
        }
    }
}
```

**Beneficios**:
- ✅ UI instantánea (datos cacheados)
- ✅ Funciona offline
- ✅ Sincroniza en background

---

## 📱 Configuración Multi-Ambiente

```kotlin
// BuildConfig.kt (generado automáticamente)
object ApiConfig {
    val BASE_URL: String
        get() = when {
            BuildConfig.DEBUG && isEmulator() -> "http://10.0.2.2:8080/"
            BuildConfig.DEBUG -> "http://192.168.1.100:8080/" // tu IP local
            else -> "https://api.movipet.com/" // producción
        }
    
    private fun isEmulator(): Boolean {
        return Build.FINGERPRINT.contains("generic")
    }
}
```

---

## 🎯 Endpoints vs Pantallas

| Endpoint | Pantalla | Acción |
|----------|----------|--------|
| `POST /api/auth/login` | LoginScreen | Click "Iniciar sesión" |
| `POST /api/auth/register` | RegisterScreen | Click "Crear Cuenta" |
| `GET /api/pets` | PetsScreen | onLaunch + pull refresh |
| `POST /api/pets` | PetsScreen | Click "Agregar mascota" |
| `GET /api/clinics` | VeterinariasScreen | onLaunch |
| `POST /api/clinics` | (Futuro) AddClinicScreen | Click "Guardar clínica" |
| `GET /api/trips` | TravelHistoryScreen | onLaunch |
| `POST /api/trips` | RatingScreen | Click "Enviar valoración" |

---

✅ **Arquitectura lista para escalabilidad y mantenibilidad**
