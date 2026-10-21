# Arquitectura

## Inspección inicial

Solo existían .idea y .git, sin fuentes, Gradle ni configuración Android. Se creó com.humant y un módulo :app; no se migró paquete previo. SDK mínimo 26, compile/target 37; herramientas disponibles: AGP 9.4.1, Gradle 9.6.0, Compose BOM 2026.02.01 y compiler 2.2.10. JDK toolchain 21, bytecode Java 17. Requisitos oficiales PAMN pendientes.

## Árbol de directorios actual

La carpeta local del repositorio se llama `HUMAN-T`; el nombre técnico configurado en Gradle es `HUMANT`. Este árbol muestra los archivos fuente y de configuración existentes. Se omiten `.git`, metadatos del IDE, configuración local y resultados de compilación.

```text
HUMAN-T/
├── .gitignore
├── AGENTS.md
├── README.md
├── docs/
│   ├── GDD.md
│   ├── ARCHITECTURE.md
│   ├── ART_GUIDE.md
│   ├── ROADMAP.md
│   └── VALIDATION.md
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
└── app/
    ├── build.gradle.kts
    └── src/
        ├── main/
        │   ├── AndroidManifest.xml
        │   ├── java/com/humant/
        │   │   ├── MainActivity.kt
        │   │   ├── core/
        │   │   │   └── time/
        │   │   │       └── ActiveClock.kt
        │   │   ├── domain/
        │   │   │   ├── GameRepository.kt
        │   │   │   ├── model/
        │   │   │   │   ├── GameState.kt
        │   │   │   │   └── Content.kt
        │   │   │   └── engine/
        │   │   │       └── EconomyEngine.kt
        │   │   ├── data/
        │   │   │   ├── local/
        │   │   │   │   ├── AtomicGameStore.kt
        │   │   │   │   └── SaveCodec.kt
        │   │   │   └── repository/
        │   │   │       └── LocalGameRepository.kt
        │   │   └── ui/
        │   │       └── screens/
        │   │           └── home/
        │   │               ├── HomeScreen.kt
        │   │               └── HomeViewModel.kt
        │   └── res/
        │       ├── drawable/
        │       │   └── ic_placeholder.xml
        │       └── xml/
        │           └── data_extraction_rules.xml
        └── test/java/com/humant/
            └── FoundationTest.kt
```

## Ampliaciones previstas — pendientes

Estas rutas se crearán cuando tengan implementaciones, contenido o pruebas reales. No forman parte todavía de la estructura existente; los nombres concretos de los motores se decidirán al implementar cada sistema.

```text
app/src/
├── main/
│   ├── java/com/humant/
│   │   ├── domain/
│   │   │   ├── model/           # Estado de partida/permanente y nuevos modelos
│   │   │   ├── engine/          # Automatización, bosses, eventos, prestigio, logros
│   │   │   └── rules/           # Reglas compartidas si necesitan paquete propio
│   │   ├── data/
│   │   │   ├── content/         # Lectores y validadores de catálogos
│   │   │   └── local/           # DTO de guardado y migraciones
│   │   └── ui/
│   │       ├── components/
│   │       ├── navigation/
│   │       ├── theme/
│   │       └── screens/
│   │           ├── shop/
│   │           ├── inventory/
│   │           ├── boss/
│   │           ├── events/
│   │           ├── prestige/
│   │           └── achievements/
│   └── assets/
│       ├── content/
│       │   ├── bosses.json
│       │   ├── items.json
│       │   ├── upgrades.json
│       │   ├── generators.json
│       │   ├── events.json
│       │   ├── stages.json
│       │   ├── prestige.json
│       │   └── achievements.json
│       └── sprites/
│           ├── phill/
│           ├── bosses/
│           ├── items/
│           └── backgrounds/
├── test/java/com/humant/         # Nuevas pruebas de reglas y persistencia
└── androidTest/java/com/humant/  # Pruebas instrumentadas de UI y lifecycle
```

Al añadir o mover archivos relevantes, actualizar el árbol actual y retirar de las previsiones las rutas ya implementadas.

## MVVM y capas

Flujo: `HomeScreen → HomeViewModel → GameRepository → EconomyEngine → AtomicGameStore → StateFlow → HomeScreen`.

- MainActivity solo enlaza ViewModel y composición.
- ui/screens/home: HomeUiState, ViewModel e interfaz desplazable. Observación con collectAsStateWithLifecycle; ninguna regla económica en recomposiciones.
- domain/model: estado inmutable y contratos pequeños de contenido por ID; domain/engine: reglas puras; GameRepository es puerto de acciones/estado sin Android.
- core/time: reloj monotónico inyectable, sesión activa y temporizador por deltas, sin Android.
- data/local: codec y archivo atómico privado; data/repository: carga, IO, transacciones y flujo observable.

Composición manual suficiente; extraer contenedor de aplicación cuando existan varios ViewModels. Sin framework de inyección ni paquetes vacíos para pantallas/motores futuros.

## Reglas y estado

EconomyEngine acredita ambos contadores y gasta solo saldo. spend es primitiva probada, todavía no tienda. BigInteger evita redondeos y techo Long; guardar decimal. Porcentajes/precios futuros usarán racionales, producción residuo entero persistido; aprobar orden de operaciones/redondeo. Validar tamaños antes de importaciones externas: memoria sigue siendo finita.

Futuro GameState separará RunState (moneda, compras, cantidades, inventario/equipo por ranura, etapa, bosses, combate/evento y temporizadores) y PermanentState (PP, nodos, logros, historial y descubrimientos aprobados). Guardar IDs y propiedad, derivar PH/clic/PH/s desde definiciones. Bonus de objetos por propiedad, nunca por equipo.

Un coordinador serializará acciones y ticks. Click/Economy distinguirá manual, automático y evento; Automation procesará producción con residuos; Boss resolverá fases mediante registro de mecánicas; Event manejará exclusión y desafíos sin PH; Prestige usará fórmula reemplazable y reinicio transaccional; Achievement reaccionará a eventos de dominio tipados. Crear motores al implementar comportamiento, no servicios ficticios. Eventos de dominio no son desafíos aleatorios. Fallo boss solo cooldown; penalización evento solo clic manual.

## Contenido ampliable

Contratos iniciales: ContentId validado, ItemDefinition, BossDefinition/PhaseDefinition y PrestigeNodeDefinition. No hay catálogo jugable ni lector JSON todavía; no crear catálogos vacíos. Futuro data/content leerá assets/content/{bosses,items,upgrades,generators,events,stages,prestige,achievements}.json.

Validar antes de publicar: IDs únicos, importes no negativos, referencias existentes, fases únicas/primera manual_ph_trial, mecánicas registradas, sprites resolubles, duraciones positivas y grafo de prerrequisitos sin ciclos. PhaseDefinition actual es contrato mínimo; parámetros tipados se incorporarán con cada mecánica, no es formato JSON definitivo. UI recibirá proyección de boss con solo fases descubiertas, nunca toda definición para renderizar secretos.

## Persistencia y errores

v1 usa UTF-8 de tres líneas: versión `1`, saldo decimal, histórico decimal. SaveCodec valida versión/campos/invariantes. Archivo game.save en filesDir; AtomicFile reemplaza estado completo con recuperación de escritura fallida. Backup Android desactivado. Solo ausencia de archivo inicia partida; versión desconocida/corrupción bloquea acciones, muestra error y conserva archivo, sin borrar silenciosamente.

Repositorio usa Dispatchers.IO y Mutex; publica solo tras guardar correctamente. ViewModel deshabilita clic mientras confirma: protege consistencia pero limita pulsación rápida. Antes de clicker completo implementar actor/cola y checkpoints con política explícita de pérdida de deltas no confirmados. Compra debe comprobar fondos/propiedad y actualizar todo en misma transacción; prestigio acreditar una vez y reemplazar RunState conjuntamente.

Al crecer, migrar a DTO estructurado (por ejemplo JSON y DataStore tipado), sin Room por defecto. Migración 1→2 conserva ambos contadores e inicializa explícitamente campos nuevos. Versión futura desconocida nunca se sobrescribe. Añadir contenido no equivale a migrar estado: IDs retirados requieren política explícita. Pruebas futuras de fallo IO, concurrencia, recuperación y muerte/reintento.

## Tiempo activo y lifecycle

ActiveClock recibe MonotonicClock; resume idempotente, sample da deltas, pause consume último y desactiva. ActiveTimer solo recibe deltas activos y satura a cero. Pruebas usan reloj falso. Todavía no conectado a Android porque no hay producción/temporizadores activos.

Futuro coordinador único de foreground, fuera de Compose, utilizará SystemClock.elapsedRealtime y lifecycle con pausa inmediata, probado ante recreación. Detener al salir y crear baseline nuevo al volver, sin restar tiempo de pared. Guardar restantes/residuos periódicamente y al pausar por misma cola serializada, no solo onDestroy. Muerte abrupta recupera checkpoint anterior: puede repetir pequeña fracción activa, nunca descontar offline. No habilitar temporizadores antes de pruebas de pausa/reanudación/recreación/muerte.

Combate consume tiempo activo de fase; penalización/cooldowns tienen restantes independientes. Automatizaciones continúan en ambos. Eventos excluyen boss/evento/penalización; fallo al salir durante evento sigue siendo propuesta pendiente, requiere marcador de resolución única si se aprueba.

## Añadir sistemas y contenido

- Boss: ID, requisito histórico, fases con IDs y primera manual, objetivos/tiempos, diálogos/recompensas/sprites. Reutilizar handlers; nueva mecánica requiere implementación pura y pruebas sin editar otros bosses. Probar proyección oculta, transiciones y cooldown sin pérdidas.
- Objeto: ID/precio/bonus/slot/sprite, registro visual/capa y validación de ranura. Compra conserva objetos anteriores; bonus por comprados. Cambiar sprite no toca economía.
- Evento: ID/tipo de desafío/objetivo/duración/penalización; scheduler comprueba exclusiones y solo tiempo activo. Clics nunca pasan por acreditación PH. Probar fallo y ausencia de premios.
- Nodo: ID/rama/coste/prerrequisitos/efectos y metadatos de conexión visual. Validar grafo, compra única y PP suficiente. Árbol se deriva de datos; nuevos efectos necesitan regla probada.

Pruebas actuales: clic, gasto suficiente/insuficiente, históricos, cifras mayores que Long/codec, formato inválido, reloj y primera fase obligatoria. Futuras: bonus, automatización/precio, evento/penalización, boss/cooldown/secretos, prestigio/permanentes/prerrequisitos/idempotencia, migraciones y lifecycle/UI instrumentados.

Referencias técnicas consultadas: [Kotlin integrado AGP](https://developer.android.com/build/migrate-to-built-in-kotlin) y [AGP 9.4](https://developer.android.com/build/releases/agp-9-4-0-release-notes).
