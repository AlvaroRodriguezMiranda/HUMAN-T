# HUMAN'T

Phill llega a la Tierra para convertirse en humano funcional: aprende costumbres, absorbe cerebros y afronta trámites como combates épicos. Videojuego incremental Android de humor adulto, macarra, sarcástico y absurdo, proyecto universitario de PAMN.

Visión: habilidades, automatizaciones, personalización por capas, eventos, cuatro bosses, prestigio ramificado y logros. **Base 0.1:** pantalla provisional, clic +1 PH, saldo/históricos separados y guardado mínimo versionado. Los demás sistemas están documentados, no implementados.

## Compilar

Abrir en Android Studio, instalar Android SDK 37 y sincronizar. Configurar SDK mediante Android Studio o local.properties (no versionado). Usar JDK 21 para Gradle. Primera resolución requiere conexión.

```powershell
.\gradlew.bat assembleDebug testDebugUnitTest lintDebug
```

En Linux/macOS: `sh ./gradlew assembleDebug testDebugUnitTest lintDebug`. Ejecutar app en dispositivo/emulador API 26+. APK: app/build/outputs/apk/debug/app-debug.apk.

En este equipo el JBR de Android Studio no pudo verificar los certificados de descarga. La validación usa el JDK instalado en `C:/Program Files/Java/jdk-21` y el almacén confiable de Windows: `$env:JAVA_HOME='C:/Program Files/Java/jdk-21'`, después `.\gradlew.bat '-Djavax.net.ssl.trustStoreType=Windows-ROOT' assembleDebug testDebugUnitTest lintDebug`. Es una opción local de Windows; no se desactiva TLS ni se impone a otros equipos.

Kotlin integrado en AGP 9.4.1, Compose compiler 2.2.10, Wrapper Gradle 9.6.0, Compose BOM 2026.02.01, MVVM y StateFlow/coroutines. Namespace/applicationId `com.humant`; minSdk 26, compile/target 37; bytecode Java 17 y toolchain JDK 21. Un módulo :app.

Inspección inicial 21/10/2026: solo .idea y .git; sin código ni configuración Android previa. Se creó com.humant, reutilizando herramientas disponibles en caché. Requisitos oficiales del profesor pendientes.

- [Reglas de agentes](AGENTS.md)
- [Diseño maestro](docs/GDD.md)
- [Arquitectura](docs/ARCHITECTURE.md)
- [Arte](docs/ART_GUIDE.md)
- [Roadmap](docs/ROADMAP.md)
- [Validación](docs/VALIDATION.md)
