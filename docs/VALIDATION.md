# Validación — 21/10/2026

## Entorno e inspección

Repositorio inicial: solo .idea y .git; sin archivos versionados, sin módulo Android, namespace o applicationId previo. Se ha creado :app con com.humant. No se han eliminado archivos existentes ni realizado commits/pushes. .idea y local.properties quedan ignorados, junto a builds, cachés, APK/AAB y claves.

Herramientas: Wrapper Gradle 9.6.0, AGP 9.4.1, Kotlin/compiler Compose 2.2.10; Android SDK 37 instalado, minSdk 26. Validación con JDK 21 instalado. JBR 25 de Android Studio falló al descargar por PKIX; se resolvió usando JDK 21 y `-Djavax.net.ssl.trustStoreType=Windows-ROOT`, conservando verificación TLS. El intento de generar Wrapper requirió saltar su comprobación inicial de URL; el Wrapper generado tiene validateDistributionUrl=true y conserva URL oficial.

## Comprobaciones

- `assembleDebug`: correcto, APK app/build/outputs/apk/debug/app-debug.apk.
- `testDebugUnitTest`: 8 pruebas, 0 fallos, 0 errores, 0 omitidas.
- Pruebas: clic acredita ambos contadores; gasto suficiente conserva histórico; fondos insuficientes no alteran estado; BigInteger mayor que Long y guardado/carga; rechazo de formato/versión/invariantes inválidos; pausa y resume idempotente del reloj; restante saturado; primera fase manual obligatoria.
- `lintDebug`: ejecución correcta, 0 errores y 6 avisos de versiones más recientes mantenidos deliberadamente. Informe regenerado con --rerun-tasks para evitar resultados incrementales antiguos. No se usa baseline para ocultar errores. Se corrigió escape de SDK local, icono provisional y reglas de backup.
- Instalación en emulador Medium_Phone_API_37.0: Success. `am start -W -n com.humant/.MainActivity`: Status ok, arranque en frío.
- UI inspeccionada por uiautomator: HUMAN'T, etapa 1, Phill placeholder, saldo 0, históricos 0, botón provisional y tasas visibles.
- Pulsación en botón: saldo 1 PH e históricos 1 PH en UI; archivo privado game.save con versión 1 y contadores 1/1.
- Force-stop y nuevo arranque en frío: UI conserva saldo 1 e históricos 1. Verifica persistencia real además del codec unitario.
- Tras reinstalar el APK, el emulador mostró transitoriamente una pantalla de actualización y timeout de arranque; una comprobación posterior confirmó MainActivity visible y ambos contadores en 1. Captura visual inspeccionada. Después se añadió enableEdgeToEdge para ajustar el contraste de barras del sistema; compilación/pruebas/lint repetidos, pero ese último ajuste visual no se volvió a probar en emulador.
- MainActivity solo conecta ViewModel y Compose. Estado observado mediante StateFlow y collectAsStateWithLifecycle; economía fuera de UI.
- Archivos obligatorios presentes en raíz/docs; Wrapper incluido, archivos generados/locales ignorados. `git diff --check` sin incidencias (los archivos nuevos siguen sin indexar por decisión de no hacer commits).

## Límites y pendientes

El emulador sufrió un primer arranque offline y aviso de System UI; segundo intento con memoria reducida y reconnect offline permitió validar. No implica problema confirmado de la app. No se han ejecutado pruebas en teléfono físico, API mínima, varios tamaños/fuentes, rotación ni muerte de proceso provocada por el sistema. Force-stop sí probado. No hay suite Android instrumentada todavía.

Solo dos contadores persisten actualmente; inventario, bosses, eventos, prestigio y logros no están implementados. Los contratos y documentación no sustituyen esas funciones ni sus pruebas. Reloj activo puro probado pero sin coordinador lifecycle de producción. Catálogos JSON, validación integral de relaciones/ciclos, migraciones y recursos finales pendientes. Cada clic espera escritura; optimizar con cola/checkpoints antes de exigir clic rápido. Falta concepto gráfico de Phill y requisitos oficiales del profesor.
